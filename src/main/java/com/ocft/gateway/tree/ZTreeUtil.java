package com.ocft.gateway.tree;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 梵高先生
 * @Date: 2019/12/11 16:41
 * @Description:
 */
public class ZTreeUtil {

    /**
     * 将资源平台返回的数据转换为本平台需要的数据格式
     * @param data 资源平台的json数据
     * @return
     */
    public ZTreeResult parseTree(String data){
        ZTreeResult result=new ZTreeResult();
        Msg msg=new Msg();
        ZyptTree tree= JSON.parseObject(data,ZyptTree.class);
        if(tree.getCode()==10000){
            msg.setStatus(MsgCode.SUCCESS);
            msg.setMessage("查询成功!");
            result.setMsg(msg);
            result.setData(convertZyptTreeToZTree(tree.getData()));
        }else{
            msg.setStatus(MsgCode.FAIL);
            msg.setMessage("资源平台数据查询出错！");
            result.setMsg(msg);
        }
        return result;
    }

    /**
     * 转换资源平台树的数据部分
     * @param datas
     * @return
     */
    private List<ZTreeVO> convertZyptTreeToZTree(List<Data> datas) {
        List<ZTreeVO> list=new ArrayList<ZTreeVO>();
        for(Data data:datas){
            ZTreeVO vo=new ZTreeVO();
            vo.setId(data.getId());
            vo.setName(data.getPointName());
            vo.setPId(0);
            vo.setSeq(data.getSeq());
            list.add(vo);
            if(data.getChild()!=null){
                list=putChildToList(vo.getId(),data.getChild(),list);
            }
        }
        return list;
    }

    /**
     * 递归转换子节点
     * @param pId 父节点
     * @param child 子节点集合
     * @param list 存放ztree节点集合的全局list
     * @return
     */
    private List<ZTreeVO> putChildToList(Integer pId, List<Child> child, List<ZTreeVO> list) {
        for(Child data:child){
            ZTreeVO vo=new ZTreeVO();
            vo.setId(data.getId());
            vo.setName(data.getPointName());
            vo.setPId(pId);
            vo.setSeq(data.getSeq());
            list.add(vo);
            if(data.getChild()!=null){
                list=putChildToList(vo.getId(),data.getChild(),list);
            }
        }
        return list;
    }

    public static void main(String[] args) {
        String data="{\n" +
                "    \"code\": 10000,\n" +
                "    \"msg\": \"SUCCESS\",\n" +
                "    \"data\": [\n" +
                "        {\n" +
                "            \"child\": [\n" +
                "                {\n" +
                "                    \"child\": [\n" +
                "                        {\n" +
                "                            \"child\": null,\n" +
                "                            \"file\": null,\n" +
                "                            \"resKnowledgeMenu\": null,\n" +
                "                            \"pointName\": \"字音\",\n" +
                "                            \"menuId\": 10,\n" +
                "                            \"seq\": 0,\n" +
                "                            \"id\": 2744\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child\": null,\n" +
                "                            \"file\": null,\n" +
                "                            \"resKnowledgeMenu\": null,\n" +
                "                            \"pointName\": \"字形\",\n" +
                "                            \"menuId\": 10,\n" +
                "                            \"seq\": 1,\n" +
                "                            \"id\": 2745\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child\": null,\n" +
                "                            \"file\": null,\n" +
                "                            \"resKnowledgeMenu\": null,\n" +
                "                            \"pointName\": \"词性\",\n" +
                "                            \"menuId\": 10,\n" +
                "                            \"seq\": 2,\n" +
                "                            \"id\": 2747\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child\": null,\n" +
                "                            \"file\": null,\n" +
                "                            \"resKnowledgeMenu\": null,\n" +
                "                            \"pointName\": \"辨析解释字义、词义\",\n" +
                "                            \"menuId\": 10,\n" +
                "                            \"seq\": 3,\n" +
                "                            \"id\": 2746\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child\": null,\n" +
                "                            \"file\": null,\n" +
                "                            \"resKnowledgeMenu\": null,\n" +
                "                            \"pointName\": \"感情色彩\",\n" +
                "                            \"menuId\": 10,\n" +
                "                            \"seq\": 4,\n" +
                "                            \"id\": 2748\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child\": null,\n" +
                "                            \"file\": null,\n" +
                "                            \"resKnowledgeMenu\": null,\n" +
                "                            \"pointName\": \"构词方式\",\n" +
                "                            \"menuId\": 10,\n" +
                "                            \"seq\": 5,\n" +
                "                            \"id\": 2749\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child\": null,\n" +
                "                            \"file\": null,\n" +
                "                            \"resKnowledgeMenu\": null,\n" +
                "                            \"pointName\": \"短语的结构\",\n" +
                "                            \"menuId\": 10,\n" +
                "                            \"seq\": 6,\n" +
                "                            \"id\": 2750\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child\": null,\n" +
                "                            \"file\": null,\n" +
                "                            \"resKnowledgeMenu\": null,\n" +
                "                            \"pointName\": \"成语\",\n" +
                "                            \"menuId\": 10,\n" +
                "                            \"seq\": 7,\n" +
                "                            \"id\": 2751\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child\": null,\n" +
                "                            \"file\": null,\n" +
                "                            \"resKnowledgeMenu\": null,\n" +
                "                            \"pointName\": \"正确使用词语、熟语\",\n" +
                "                            \"menuId\": 10,\n" +
                "                            \"seq\": 8,\n" +
                "                            \"id\": 2752\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child\": null,\n" +
                "                            \"file\": null,\n" +
                "                            \"resKnowledgeMenu\": null,\n" +
                "                            \"pointName\": \"歇后语，谚语\",\n" +
                "                            \"menuId\": 10,\n" +
                "                            \"seq\": 9,\n" +
                "                            \"id\": 2753\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child\": null,\n" +
                "                            \"file\": null,\n" +
                "                            \"resKnowledgeMenu\": null,\n" +
                "                            \"pointName\": \"关联词语\",\n" +
                "                            \"menuId\": 10,\n" +
                "                            \"seq\": 10,\n" +
                "                            \"id\": 2754\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"file\": null,\n" +
                "                    \"resKnowledgeMenu\": null,\n" +
                "                    \"pointName\": \"词汇\",\n" +
                "                    \"menuId\": 10,\n" +
                "                    \"seq\": 0,\n" +
                "                    \"id\": 2743\n" +
                "                },\n" +
                "                {\n" +
                "                    \"child\": [\n" +
                "                        {\n" +
                "                            \"child\": null,\n" +
                "                            \"file\": null,\n" +
                "                            \"resKnowledgeMenu\": null,\n" +
                "                            \"pointName\": \"标点符号\",\n" +
                "                            \"menuId\": 10,\n" +
                "                            \"seq\": 0,\n" +
                "                            \"id\": 2756\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child\": null,\n" +
                "                            \"file\": null,\n" +
                "                            \"resKnowledgeMenu\": null,\n" +
                "                            \"pointName\": \"语句停顿\",\n" +
                "                            \"menuId\": 10,\n" +
                "                            \"seq\": 1,\n" +
                "                            \"id\": 2757\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child\": null,\n" +
                "                            \"file\": null,\n" +
                "                            \"resKnowledgeMenu\": null,\n" +
                "                            \"pointName\": \"语气\",\n" +
                "                            \"menuId\": 10,\n" +
                "                            \"seq\": 2,\n" +
                "                            \"id\": 2758\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child\": null,\n" +
                "                            \"file\": null,\n" +
                "                            \"resKnowledgeMenu\": null,\n" +
                "                            \"pointName\": \"修辞方法\",\n" +
                "                            \"menuId\": 10,\n" +
                "                            \"seq\": 3,\n" +
                "                            \"id\": 2759\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child\": null,\n" +
                "                            \"file\": null,\n" +
                "                            \"resKnowledgeMenu\": null,\n" +
                "                            \"pointName\": \"连词成句\",\n" +
                "                            \"menuId\": 10,\n" +
                "                            \"seq\": 4,\n" +
                "                            \"id\": 2760\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child\": null,\n" +
                "                            \"file\": null,\n" +
                "                            \"resKnowledgeMenu\": null,\n" +
                "                            \"pointName\": \"排列句子顺序\",\n" +
                "                            \"menuId\": 10,\n" +
                "                            \"seq\": 5,\n" +
                "                            \"id\": 2761\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child\": null,\n" +
                "                            \"file\": null,\n" +
                "                            \"resKnowledgeMenu\": null,\n" +
                "                            \"pointName\": \"病句辨析\",\n" +
                "                            \"menuId\": 10,\n" +
                "                            \"seq\": 6,\n" +
                "                            \"id\": 2762\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child\": null,\n" +
                "                            \"file\": null,\n" +
                "                            \"resKnowledgeMenu\": null,\n" +
                "                            \"pointName\": \"选用，仿用，变换句式\",\n" +
                "                            \"menuId\": 10,\n" +
                "                            \"seq\": 7,\n" +
                "                            \"id\": 2763\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child\": null,\n" +
                "                            \"file\": null,\n" +
                "                            \"resKnowledgeMenu\": null,\n" +
                "                            \"pointName\": \"划分句子成分及复句关系\",\n" +
                "                            \"menuId\": 10,\n" +
                "                            \"seq\": 8,\n" +
                "                            \"id\": 2764\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child\": null,\n" +
                "                            \"file\": null,\n" +
                "                            \"resKnowledgeMenu\": null,\n" +
                "                            \"pointName\": \"对联\",\n" +
                "                            \"menuId\": 10,\n" +
                "                            \"seq\": 9,\n" +
                "                            \"id\": 2765\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child\": null,\n" +
                "                            \"file\": null,\n" +
                "                            \"resKnowledgeMenu\": null,\n" +
                "                            \"pointName\": \"补充句子\",\n" +
                "                            \"menuId\": 10,\n" +
                "                            \"seq\": 10,\n" +
                "                            \"id\": 2766\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child\": null,\n" +
                "                            \"file\": null,\n" +
                "                            \"resKnowledgeMenu\": null,\n" +
                "                            \"pointName\": \"句式\",\n" +
                "                            \"menuId\": 10,\n" +
                "                            \"seq\": 11,\n" +
                "                            \"id\": 2767\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child\": null,\n" +
                "                            \"file\": null,\n" +
                "                            \"resKnowledgeMenu\": null,\n" +
                "                            \"pointName\": \"说明方法\",\n" +
                "                            \"menuId\": 10,\n" +
                "                            \"seq\": 12,\n" +
                "                            \"id\": 2768\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"file\": null,\n" +
                "                    \"resKnowledgeMenu\": null,\n" +
                "                    \"pointName\": \"句子\",\n" +
                "                    \"menuId\": 10,\n" +
                "                    \"seq\": 1,\n" +
                "                    \"id\": 2755\n" +
                "                },\n" +
                "                {\n" +
                "                    \"child\": [\n" +
                "                        {\n" +
                "                            \"child\": null,\n" +
                "                            \"file\": null,\n" +
                "                            \"resKnowledgeMenu\": null,\n" +
                "                            \"pointName\": \"文言实词\",\n" +
                "                            \"menuId\": 10,\n" +
                "                            \"seq\": 0,\n" +
                "                            \"id\": 2770\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child\": null,\n" +
                "                            \"file\": null,\n" +
                "                            \"resKnowledgeMenu\": null,\n" +
                "                            \"pointName\": \"文言虚词\",\n" +
                "                            \"menuId\": 10,\n" +
                "                            \"seq\": 1,\n" +
                "                            \"id\": 2771\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child\": null,\n" +
                "                            \"file\": null,\n" +
                "                            \"resKnowledgeMenu\": null,\n" +
                "                            \"pointName\": \"词类活用\",\n" +
                "                            \"menuId\": 10,\n" +
                "                            \"seq\": 2,\n" +
                "                            \"id\": 2772\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child\": null,\n" +
                "                            \"file\": null,\n" +
                "                            \"resKnowledgeMenu\": null,\n" +
                "                            \"pointName\": \"古今异义\",\n" +
                "                            \"menuId\": 10,\n" +
                "                            \"seq\": 3,\n" +
                "                            \"id\": 2773\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child\": null,\n" +
                "                            \"file\": null,\n" +
                "                            \"resKnowledgeMenu\": null,\n" +
                "                            \"pointName\": \"通假字\",\n" +
                "                            \"menuId\": 10,\n" +
                "                            \"seq\": 4,\n" +
                "                            \"id\": 2774\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child\": null,\n" +
                "                            \"file\": null,\n" +
                "                            \"resKnowledgeMenu\": null,\n" +
                "                            \"pointName\": \"一词多义\",\n" +
                "                            \"menuId\": 10,\n" +
                "                            \"seq\": 5,\n" +
                "                            \"id\": 2775\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child\": null,\n" +
                "                            \"file\": null,\n" +
                "                            \"resKnowledgeMenu\": null,\n" +
                "                            \"pointName\": \"文言句式\",\n" +
                "                            \"menuId\": 10,\n" +
                "                            \"seq\": 6,\n" +
                "                            \"id\": 2776\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child\": null,\n" +
                "                            \"file\": null,\n" +
                "                            \"resKnowledgeMenu\": null,\n" +
                "                            \"pointName\": \"翻译句子\",\n" +
                "                            \"menuId\": 10,\n" +
                "                            \"seq\": 7,\n" +
                "                            \"id\": 2777\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"file\": null,\n" +
                "                    \"resKnowledgeMenu\": null,\n" +
                "                    \"pointName\": \"古代汉语知识\",\n" +
                "                    \"menuId\": 10,\n" +
                "                    \"seq\": 2,\n" +
                "                    \"id\": 2769\n" +
                "                },\n" +
                "                {\n" +
                "                    \"child\": [\n" +
                "                        {\n" +
                "                            \"child\": null,\n" +
                "                            \"file\": null,\n" +
                "                            \"resKnowledgeMenu\": null,\n" +
                "                            \"pointName\": \"文学常识\",\n" +
                "                            \"menuId\": 10,\n" +
                "                            \"seq\": 0,\n" +
                "                            \"id\": 2779\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"child\": null,\n" +
                "                            \"file\": null,\n" +
                "                            \"resKnowledgeMenu\": null,\n" +
                "                            \"pointName\": \"诗文默写\",\n" +
                "                            \"menuId\": 10,\n" +
                "                            \"seq\": 1,\n" +
                "                            \"id\": 2780\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"file\": null,\n" +
                "                    \"resKnowledgeMenu\": null,\n" +
                "                    \"pointName\": \"识记\",\n" +
                "                    \"menuId\": 10,\n" +
                "                    \"seq\": 3,\n" +
                "                    \"id\": 2778\n" +
                "                }\n" +
                "            ],\n" +
                "            \"file\": null,\n" +
                "            \"resKnowledgeMenu\": null,\n" +
                "            \"pointName\": \"基础知识\",\n" +
                "            \"menuId\": 10,\n" +
                "            \"seq\": 62,\n" +
                "            \"id\": 2742\n" +
                "        },\n" +
                "        {\n" +
                "            \"child\": [\n" +
                "                {\n" +
                "                    \"child\": null,\n" +
                "                    \"file\": null,\n" +
                "                    \"resKnowledgeMenu\": null,\n" +
                "                    \"pointName\": \"文言文阅读\",\n" +
                "                    \"menuId\": 10,\n" +
                "                    \"seq\": 0,\n" +
                "                    \"id\": 2782\n" +
                "                },\n" +
                "                {\n" +
                "                    \"child\": null,\n" +
                "                    \"file\": null,\n" +
                "                    \"resKnowledgeMenu\": null,\n" +
                "                    \"pointName\": \"古诗词阅读\",\n" +
                "                    \"menuId\": 10,\n" +
                "                    \"seq\": 1,\n" +
                "                    \"id\": 2783\n" +
                "                },\n" +
                "                {\n" +
                "                    \"child\": null,\n" +
                "                    \"file\": null,\n" +
                "                    \"resKnowledgeMenu\": null,\n" +
                "                    \"pointName\": \"现代文阅读\",\n" +
                "                    \"menuId\": 10,\n" +
                "                    \"seq\": 2,\n" +
                "                    \"id\": 2784\n" +
                "                },\n" +
                "                {\n" +
                "                    \"child\": null,\n" +
                "                    \"file\": null,\n" +
                "                    \"resKnowledgeMenu\": null,\n" +
                "                    \"pointName\": \"现代诗歌阅读\",\n" +
                "                    \"menuId\": 10,\n" +
                "                    \"seq\": 3,\n" +
                "                    \"id\": 2785\n" +
                "                }\n" +
                "            ],\n" +
                "            \"file\": null,\n" +
                "            \"resKnowledgeMenu\": null,\n" +
                "            \"pointName\": \"阅读理解及鉴赏\",\n" +
                "            \"menuId\": 10,\n" +
                "            \"seq\": 63,\n" +
                "            \"id\": 2781\n" +
                "        },\n" +
                "        {\n" +
                "            \"child\": [\n" +
                "                {\n" +
                "                    \"child\": null,\n" +
                "                    \"file\": null,\n" +
                "                    \"resKnowledgeMenu\": null,\n" +
                "                    \"pointName\": \"综合读写\",\n" +
                "                    \"menuId\": 10,\n" +
                "                    \"seq\": 0,\n" +
                "                    \"id\": 2787\n" +
                "                },\n" +
                "                {\n" +
                "                    \"child\": null,\n" +
                "                    \"file\": null,\n" +
                "                    \"resKnowledgeMenu\": null,\n" +
                "                    \"pointName\": \"口语交际\",\n" +
                "                    \"menuId\": 10,\n" +
                "                    \"seq\": 1,\n" +
                "                    \"id\": 2788\n" +
                "                },\n" +
                "                {\n" +
                "                    \"child\": null,\n" +
                "                    \"file\": null,\n" +
                "                    \"resKnowledgeMenu\": null,\n" +
                "                    \"pointName\": \"扩展语段\",\n" +
                "                    \"menuId\": 10,\n" +
                "                    \"seq\": 2,\n" +
                "                    \"id\": 2789\n" +
                "                },\n" +
                "                {\n" +
                "                    \"child\": null,\n" +
                "                    \"file\": null,\n" +
                "                    \"resKnowledgeMenu\": null,\n" +
                "                    \"pointName\": \"压缩语段\",\n" +
                "                    \"menuId\": 10,\n" +
                "                    \"seq\": 3,\n" +
                "                    \"id\": 2790\n" +
                "                },\n" +
                "                {\n" +
                "                    \"child\": null,\n" +
                "                    \"file\": null,\n" +
                "                    \"resKnowledgeMenu\": null,\n" +
                "                    \"pointName\": \"理解句子\",\n" +
                "                    \"menuId\": 10,\n" +
                "                    \"seq\": 4,\n" +
                "                    \"id\": 2791\n" +
                "                },\n" +
                "                {\n" +
                "                    \"child\": null,\n" +
                "                    \"file\": null,\n" +
                "                    \"resKnowledgeMenu\": null,\n" +
                "                    \"pointName\": \"课文理解，名著阅读\",\n" +
                "                    \"menuId\": 10,\n" +
                "                    \"seq\": 5,\n" +
                "                    \"id\": 2792\n" +
                "                }\n" +
                "            ],\n" +
                "            \"file\": null,\n" +
                "            \"resKnowledgeMenu\": null,\n" +
                "            \"pointName\": \"语言表达及应用\",\n" +
                "            \"menuId\": 10,\n" +
                "            \"seq\": 64,\n" +
                "            \"id\": 2786\n" +
                "        },\n" +
                "        {\n" +
                "            \"child\": [\n" +
                "                {\n" +
                "                    \"child\": null,\n" +
                "                    \"file\": null,\n" +
                "                    \"resKnowledgeMenu\": null,\n" +
                "                    \"pointName\": \"材料作文\",\n" +
                "                    \"menuId\": 10,\n" +
                "                    \"seq\": 0,\n" +
                "                    \"id\": 2794\n" +
                "                },\n" +
                "                {\n" +
                "                    \"child\": null,\n" +
                "                    \"file\": null,\n" +
                "                    \"resKnowledgeMenu\": null,\n" +
                "                    \"pointName\": \"话题作文\",\n" +
                "                    \"menuId\": 10,\n" +
                "                    \"seq\": 1,\n" +
                "                    \"id\": 2795\n" +
                "                },\n" +
                "                {\n" +
                "                    \"child\": null,\n" +
                "                    \"file\": null,\n" +
                "                    \"resKnowledgeMenu\": null,\n" +
                "                    \"pointName\": \"命题作文、半命题作文\",\n" +
                "                    \"menuId\": 10,\n" +
                "                    \"seq\": 2,\n" +
                "                    \"id\": 2796\n" +
                "                },\n" +
                "                {\n" +
                "                    \"child\": null,\n" +
                "                    \"file\": null,\n" +
                "                    \"resKnowledgeMenu\": null,\n" +
                "                    \"pointName\": \"其他作文\",\n" +
                "                    \"menuId\": 10,\n" +
                "                    \"seq\": 3,\n" +
                "                    \"id\": 2797\n" +
                "                }\n" +
                "            ],\n" +
                "            \"file\": null,\n" +
                "            \"resKnowledgeMenu\": null,\n" +
                "            \"pointName\": \"写作\",\n" +
                "            \"menuId\": 10,\n" +
                "            \"seq\": 65,\n" +
                "            \"id\": 2793\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        ZTreeResult result=new ZTreeUtil().parseTree(data);
        System.out.println(JSON.toJSONString(result));
    }

}
