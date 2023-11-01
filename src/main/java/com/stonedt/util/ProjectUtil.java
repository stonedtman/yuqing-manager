package com.stonedt.util;

import org.apache.commons.lang3.StringUtils;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * description: 方案相关的工具类 <br>
 * date: 2020/4/14 15:17 <br>
 * author: huajiancheng <br>
 * version: 1.0 <br>
 */
public class ProjectUtil {






    /**
     * @param [keyword]
     * @return java.lang.String
     * @description: 处理词 <br>
     * @version: 1.0 <br>
     * @date: 2020/5/6 19:38 <br>
     * @author: huajiancheng <br>
     */

    public static String dealProjectWords(String keyword) {
        String response = "";
        try {
            if (keyword != null) {
                if (keyword.contains("，")) {
                    keyword = keyword.replaceAll("，", ",");
                }
                String keywords[] = keyword.split(",");
                List<String> keywordList = new ArrayList<String>();
                for (int i = 0; i < keywords.length; i++) {
                    String currentKeyword = keywords[i];

                    if (currentKeyword.contains("\n") || currentKeyword.contains("\t") || currentKeyword.contains("\r")) {
                        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                        Matcher m = p.matcher(currentKeyword);
                        currentKeyword = m.replaceAll("");
                    }

                    if (!currentKeyword.equals("") && currentKeyword != "") {
                        keywordList.add(currentKeyword);
                    }
                }
                response = StringUtils.join(keywordList, ",");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 合并相同关键词
     */
    public static String mergeProjectWords(String keyword) {
        //判断是高级方案还是普通方案
        if (keyword.contains(",")) {
            //普通方案
            String[] keywords = keyword.split(",");
            Set<String> keywordSet = new HashSet<String>(Arrays.asList(keywords));
            return StringUtils.join(keywordSet, ",");
        } else if (keyword.contains("|") && !keyword.contains("+")&& !keyword.contains("(")&& !keyword.contains(")")) {
            String[] keywords = keyword.split("\\|");
            Set<String> keywordSet = new HashSet<String>(Arrays.asList(keywords));
            return StringUtils.join(keywordSet, "|");
        }
//        else {
//            //高级方案，高级方案使用|表示或，使用+表示与，使用()表示组合,合并相同的关键词/组合
//            //todo: 1.组装成树形结构
//            keyword = "(" + keyword + ")";
//            TreeNode head = new TreeNode();
//            TreeNode now = head;
//            char[] charArray = keyword.toCharArray();
//            for (char c : charArray) {
//                if (c == '(') {
//                    //1.1如果是(，则创建一个新的节点，将当前节点作为父节点
//                    TreeNode node = new TreeNode();
//                    now.children.add(node);
//                    node.parent = now;
//                    now.value += c;
//                    now = node;
//                } else if (c == ')') {
//                    //1.2如果是)，则将当前节点的父节点作为当前节点
//                    now.value += c;
//                    now = now.parent;
//                }else {
//                    //1.3如果是其他字符，则将当前字符加入到当前节点的value中
//                    now.value += c;
//                }
//
//
//            }
//            //
//            now = head;
//            //todo: 2.遍历树形结构，合并相同的关键词/组合
//            mergeProjectWordsByTreeNode(now);
//
//
//
//            //todo: 3.遍历树形结构，将树形结构转换成字符串
//            response = getProjectWordsByTreeNode(head,"");
//
//
//        }
        return keyword;
    }

    private static void mergeProjectWordsByTreeNode(TreeNode now) {
        if (now.children == null || now.children.size() == 0) {
            return;
        }
        String value = now.value;
        String[] split = value.split("\\|");
        Set<String> set = new HashSet<String>(Arrays.asList(split));
        Set<String> newSet = new HashSet<String>();
        for (String s : set) {
            String[] plusSpilt = s.split("\\+");
            //排序
            Arrays.sort(plusSpilt);
            String plusString = StringUtils.join(plusSpilt, "+");
            newSet.add(plusString);
        }
        String newValue = StringUtils.join(newSet, "|");
        now.value = newValue;
        for (TreeNode child : now.children) {
            mergeProjectWordsByTreeNode(child);
        }
    }

    private static String getProjectWordsByTreeNode(TreeNode head,String response) {
        //后序遍历
        if (head.children == null || head.children.size() == 0) {
            return head.value;
        }
        String value = head.value;
        if (value.equals("")) {
            response += "(";
        }else {
            response += value;
        }
        for (TreeNode child : head.children) {
            response = getProjectWordsByTreeNode(child,response);
        }
        response += ")";
        return response;
    }

    static class TreeNode{
        String value = new String("");
        List<TreeNode> children = new ArrayList<TreeNode>();

        TreeNode parent;

    }

}
