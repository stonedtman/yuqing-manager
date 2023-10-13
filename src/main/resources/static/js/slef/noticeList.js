$(function () {
    $('#head').html('公告管理')
    $('#navigation').html('公告管理')
    loading(1)
})

function loading(pageNum) {
    $.ajax({
        type: "get",
        url: ctxPath + "notice/getNoticeList?pageNum=" + pageNum,
        dataType: "json",
        beforeSend: function () {
            $("#noticelist").html('');
        },
        compvare: function () {
        },
        success: function (res) {
            console.log(res)
            var data = res.data;
            var code = res.status;
            var totalCount = data.size;
            var totalPage = data.pages;
            var page = data.pageNum;
            if (page === 1) {
                paging(totalCount, totalPage, page);
            }
            if (code === 200) {
                var list = data.list;
                // paging(totalCount, totalPage, page);
                var htmlStr = '';
                for (var i = 0; i < list.length; i++) {
                    var dataJson = list[i];


                    /* <th>机构公共id</th>    organization_id
                                <th>机构全称</th>   organization_name
                                <th>机构简称</th>   organization_short
                                <th>机构类型</th>        organization_type
                                <th>有效期</th>      term_of_validity
                                <th>状态</th>       status
                                <!-- <th>创建日期</th>-->    create_time
                                <th>组织代码</th>      organization_code
                                <th>logo地址</th>      logo_url
                                <th>系统名称</th>         system_title*/
                    let status;
                    switch (dataJson.status) {
                        case 0:
                            status = '<span style="color: #d9534f">已下架</span>';
                            break;
                        case 1:
                            status = '<span style="color: #4cae4c">公告中</span>';
                            break;
                        default:
                            status = '<span style="color: #970dba">已下架</span>';

                    }

                    htmlStr = '<tr class="textAlign">' +
                        /*'<td>' + organization_id + '</td>' +*/
                        '<td>' + dataJson.content + '</td>' +
                        '<td>' + dataJson.username + '</td>' +
                        '<td>' + dataJson.publish_time + '</td>' +
                        '<td>' + status + '</td>' +
                        '<td>' +
                        '<div class="btn-group">' +
                        '<button class="btn-primary btn btn-xs" onclick="grounding('+dataJson.id+')" >' + '上架' + '</button>'+
                        '<button class="btn-danger btn btn-xs" onclick="offShelf('+dataJson.id+')" >' + '下架' + '</button>'+
                        '</div>' +
                        '</td>' +
                        '</tr>'
                    $("#noticelist").append(htmlStr);
                }
            } else {
                $("#noticelist").html('<tr><td colspan="11">暂无数据！</td></tr>');
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
        if (xhr.status == 403) {
            window.location.href = ctxPath + "login";
        }
    }
    });
}

function paging(totalData, totalPage, pagenum) {
    $('#box').paging({
        initPageNo: pagenum, // 初始页码
        totalPages: totalPage, //总页数
        totalCount: '合计' + totalData + '条数据', // 条目总数
        slideSpeed: 600, // 缓动速度 单位毫秒
        jump: true, //是否支持跳转
        callback: function (page) {
            // 回调函数
            //loading(page);
        }
    });
}

function JumpToPage(pagenum) {
    loading(pagenum);
}




function stampTOtime(data) {
//data为时间戳
    var date = new Date(data);
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
    var D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + ' ';
    var h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
    var m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
    var s = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds());

    var strDate = Y + M + D + h + m + s; //转换为年月日时分秒
    // var strDate = Y+M+D; //转换为年月日

    return strDate;
}

function grounding(id) {
    $.ajax({
        type: "get",
        url: ctxPath + "notice/upNotice?id="+id,
        dataType: "json",
        success: function (res) {
            var code = res.status;
            if (code === 200) {
                //bootsrtap弹出框
                alert("上架成功")
                loading(1)
            } else {
                alert("发布失败")
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
        if (xhr.status == 403) {
            window.location.href = ctxPath + "login";
        }
    }
    });
}

function offShelf(id) {
    $.ajax({
        type: "get",
        url: ctxPath + "notice/deleteNotice?id="+id,
        dataType: "json",
        success: function (res) {
            var code = res.status;
            if (code === 200) {
                //bootsrtap弹出框
                alert("下架成功")
                loading(1)
            } else {
                alert("下架失败")
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
        if (xhr.status == 403) {
            window.location.href = ctxPath + "login";
        }
    }
    });
}