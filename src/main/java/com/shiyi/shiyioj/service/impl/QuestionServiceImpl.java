package com.shiyi.shiyioj.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.shiyi.shiyioj.common.ErrorCode;
import com.shiyi.shiyioj.constant.CommonConstant;
import com.shiyi.shiyioj.exception.BusinessException;
import com.shiyi.shiyioj.exception.ThrowUtils;

import com.shiyi.shiyioj.model.dto.question.QuestionQueryRequest;
import com.shiyi.shiyioj.model.entity.*;
import com.shiyi.shiyioj.model.vo.QuestionAllVo;
import com.shiyi.shiyioj.model.vo.QuestionVo;
import com.shiyi.shiyioj.model.vo.UserVO;
import com.shiyi.shiyioj.service.QuestionService;
import com.shiyi.shiyioj.service.QuestionSubmitService;
import com.shiyi.shiyioj.service.UserService;
import com.shiyi.shiyioj.utils.SqlUtils;
import com.shiyi.shiyioj.mapper.QuestionMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author 34011
* @description 针对表【question(帖子)】的数据库操作Service实现
* @createDate 2024-12-28 21:13:03
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService {

    @Lazy
    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;


    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public void validQuestion(Question question, boolean add) {
        if (question == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String title = question.getTitle();
        String content = question.getContent();
        String tags = question.getTags();
        String answer = question.getAnswer();
        String judgeCase = question.getJudgeCase();
        String judgeConfig = question.getJudgeConfig();

        // 创建时，参数不能为空
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(title, content, tags), ErrorCode.PARAMS_ERROR);
        }
        // 有参数则校验
        if (StringUtils.isNotBlank(title) && title.length() > 80) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "标题过长");
        }
        if (StringUtils.isNotBlank(content) && content.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "内容过长");
        }
        if(StringUtils.isNotBlank(answer) && answer.length() > 8192){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "答案过长");
        }
        if(StringUtils.isNotBlank(judgeCase) && judgeCase.length() > 8192){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "测试用例过长");
        }
        if(StringUtils.isNotBlank(judgeConfig) && judgeConfig.length() > 8192){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "评测配置过长");
        }
    }

    /**
     * 获取查询包装类
     *
     * @param questionQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        if (questionQueryRequest == null) {
            return queryWrapper;
        }
        Long id = questionQueryRequest.getId();
        String title = questionQueryRequest.getTitle();
        String content = questionQueryRequest.getContent();
        List<String> tags = questionQueryRequest.getTags();
        String answer = questionQueryRequest.getAnswer();
        Long userId = questionQueryRequest.getUserId();
        String sortField = questionQueryRequest.getSortField();
        String sortOrder = questionQueryRequest.getSortOrder();

        queryWrapper.like(StringUtils.isNotBlank(title), "title", title);
        queryWrapper.like(StringUtils.isNotBlank(content), "content", content);
        queryWrapper.like(StringUtils.isNotBlank(answer), "answer", answer);
        if (CollUtil.isNotEmpty(tags)) {
            for (String tag : tags) {
                queryWrapper.like("tags", "\"" + tag + "\"");
            }
        }
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public QuestionVo getQuestionVo(Question question, HttpServletRequest request) {
        QuestionVo questionVO = QuestionVo.objToVo(question);
        // 1. 关联查询用户信息
        Long userId = question.getUserId();
        User user = null;
        if (userId != null && userId > 0) {
            user = userService.getById(userId);
        }
        UserVO userVO = userService.getUserVO(user);
        questionVO.setUserVO(userVO);
        return questionVO;
    }

    @Override
    public Page<QuestionVo> getQuestionVoPage(Page<Question> questionPage, HttpServletRequest request) {
        List<Question> questionList = questionPage.getRecords();
        Page<QuestionVo> questionVOPage = new Page<>(questionPage.getCurrent(), questionPage.getSize(), questionPage.getTotal());
        if (CollUtil.isEmpty(questionList)) {
            return questionVOPage;
        }
         //1. 关联查询用户信息
        Set<Long> userIdSet = questionList.stream().map(Question::getUserId).collect(Collectors.toSet());
        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream()
                .collect(Collectors.groupingBy(User::getId));

        // 填充信息
        List<QuestionVo> questionVOList = questionList.stream().map(question -> {
            QuestionVo questionVO = QuestionVo.objToVo(question);
            Long userId = question.getUserId();
            User user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId).getFirst();
            }
            questionVO.setUserVO(userService.getUserVO(user));
            return questionVO;
        }).collect(Collectors.toList());
        List<QuestionVo> list = questionVOList.stream().map(questionVo -> questionVo
                .setSubmitNum(Math.toIntExact(questionSubmitService.getQuestionSubmitCount(questionVo.getId())))
                .setAcceptNum(Math.toIntExact(questionSubmitService.getQuestionAcceptCount(questionVo.getId())))).toList();
        questionVOPage.setRecords(list);
        return questionVOPage;
    }

    @Override
    public Page<QuestionAllVo> getQuestionAllVoPage(Page<Question> questionPage) {
        List<Question> questionList = questionPage.getRecords();
        Page<QuestionAllVo> questionVOPage = new Page<>(questionPage.getCurrent(), questionPage.getSize(), questionPage.getTotal());
        if (CollUtil.isEmpty(questionList)) {
            return questionVOPage;
        }
        // 将questionList中的数据转换为QuestionAllVo
        List<QuestionAllVo> questionAllVoList = questionList.stream().map(QuestionAllVo::objToVo).toList();
        List<QuestionAllVo> list = questionAllVoList.stream().map(questionAllVo -> questionAllVo
                .setSubmitNum(Math.toIntExact(questionSubmitService.getQuestionSubmitCount(questionAllVo.getId())))
                .setAcceptNum(Math.toIntExact(questionSubmitService.getQuestionAcceptCount(questionAllVo.getId())))).toList();
        questionVOPage.setRecords(list);
        return questionVOPage;
    }


}




