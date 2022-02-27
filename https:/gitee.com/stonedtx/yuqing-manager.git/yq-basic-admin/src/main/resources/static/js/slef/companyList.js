$(function () {
    $('#head').html('企业管理')
    $('#navigation').html('企业管理')
    loading(1)
})

function loading(pagenum) {
    $.ajax({
        type: "POST",
        url: ctxPath + "company/getCompanyList",
        dataType: "json",
        data: {page: pagenum},
        beforeSend: function () {
            $("#companylist").html('');
        },
        compvare: function () {
        },
        success: function (res) {
            var code = res.code;
            var totalCount = res.totalCount;
            var totalPage = res.totalPage;
            var page = res.page;
            if (page == 1) {
                paging(totalCount, totalPage, page);
            }
            if (code == 200) {
                console.log(res)
                var data = res.data;
                // paging(totalCount, totalPage, page);
                var htmlStr = '';
                for (var i = 0; i < data.length; i++) {
                    var dataJson = data[i];


                    //创建时间
                    if (dataJson.create_time != null && dataJson.create_time != '') {
                        var create_time = stampTOtime(dataJson.create_time);
                    }else{
                        var create_time = '——————————';
                    }

                    //机构公共id
                    if (dataJson.organization_id != null && dataJson.organization_id != '') {
                        var organization_id = dataJson.organization_id;
                    }else{
                        var organization_id = '——————————';
                    }


                    //机构简称
                    if (dataJson.organization_short != null && dataJson.organization_short != '') {
                        var organization_short = dataJson.organization_short;
                    }else{
                        var organization_short = '——————————';
                    }

                    //机构名全称
                    if (dataJson.organization_name != null && dataJson.organization_name != '') {
                        var organization_name = dataJson.organization_name;
                    }else{
                        var organization_name = '——————————';
                    }

                    //机构类型（1机构、2个人）
                    if (dataJson.organization_type != null && dataJson.organization_type != '') {
                        var organization_type = dataJson.organization_type;
                        if (organization_type == 1){
                            organization_type = '机构';
                        }else if (organization_type == 2) {
                            organization_type = '个人';
                        }else{
                            var organization_type = '——————————';
                        }
                    }else{
                        var organization_type = '——————————';
                    }

                    //有效期
                    if (dataJson.term_of_validity != null && dataJson.term_of_validity != '') {
                        var term_of_validity = stampTOtime(dataJson.term_of_validity);
                        term_of_validity = term_of_validity.substring(0 , 10);
                    }else{
                        var term_of_validity = '——————————';
                    }

                    //状态（1代表正常 2代表注销）
                    if (dataJson.status != null && dataJson.status != '') {
                        var status = dataJson.status;
                        if (status == 1){
                            status = '正常';
                        }else if (status == 2) {
                            status = '注销';
                        }else{
                            var status = '——————————';
                        }
                    }else{
                        var status = '——————————';
                    }


                    //组织代码
                    if (dataJson.organization_code != null && dataJson.organization_code != '') {
                        var organization_code = dataJson.organization_code;
                    }else{
                        var organization_code = '——————————';
                    }

                    //logo地址
                    if (dataJson.logo_url != null && dataJson.logo_url != '') {
                        var logo_url = dataJson.logo_url;
                    }else{
                        var logo_url = '——————————';
                    }

                    //系统名称
                    if (dataJson.system_title != null && dataJson.system_title != '') {
                        var system_title = dataJson.system_title;
                    }else{
                        var system_title = '——————————';
                    }

                    if (dataJson.count != null && dataJson.count != '') {
                        var count = dataJson.count;
                    }else{
                        var count = '——————————';
                    }

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


                    htmlStr = '<tr class="textAlign">' +
                        /*'<td>' + organization_id + '</td>' +*/
                        '<td data-code="' + organization_code + '" data-id="' + organization_id + '" onclick="toUserList(' + '\'' +organization_id+ '\'' +')"><a>' + organization_name + '</a></td>' +
                        '<td data-code="' + organization_code + '" data-id="' + organization_id + '">' + count + '</td>' +
                        '<td>'+organization_type+'</td>' +
                        '<td>' + term_of_validity + '</td>' +
                        '<td>' + status + '</td>' +
                        /*'<td>' + create_time + '</td>' +*/
                        '<td>' + organization_code + '</td>' +
                        '<td>' + logo_url + '</td>' +
                        '<td>' + system_title + '</td>' +
                        '<td>' +
                        '<div class="btn-group">' +
                        '<button class="btn-info btn btn-xs" onclick="update('+dataJson.id+')" >' + '修改' + '</button>'+
                        '</div>' +
                        '</td>' +
                        '</tr>'
                    $("#companylist").append(htmlStr);
                }
            } else {
                $("#companylist").html('<tr><td colspan="11">暂无数据！</td></tr>');
            }
        },
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


function update(id) {

    location.href= "/company/updateCompany?id="+id;
}

function toUserList(organization_id) {

    location.href="/company/toUserList?organization_id="+organization_id;

}