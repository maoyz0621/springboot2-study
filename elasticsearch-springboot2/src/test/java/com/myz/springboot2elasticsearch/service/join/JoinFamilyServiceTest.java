package com.myz.springboot2elasticsearch.service.join;

import com.myz.springboot2elasticsearch.es.constants.MemberTypeEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author maoyz0621 on 2022/4/21
 * @version v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JoinFamilyServiceTest {

    @Resource
    JoinFamilyService joinFamilyService;

    @Test
    public void addFamilyMember() throws Exception {
        joinFamilyService.addFamilyMember("1", "haha", MemberTypeEnum.Parent);
        joinFamilyService.addFamilyMember("2", "haha12", MemberTypeEnum.GrandParent);
    }
}