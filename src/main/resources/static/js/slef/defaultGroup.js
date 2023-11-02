$('#head').html('默认方案管理')
$('#navigation').html('默认方案管理')
loading(1);

function loading(pagenum) {

    let dataObject = new Object();

    dataObject.page = pagenum;

    $.ajax({
        type: "POST",
        url: "/defaultGroup/getDefaultGroupList",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(dataObject),
        beforeSend: function () {
            $("#defaultGroup").html('');
        },
        complete: function () {
        },
        success: function (res) {
            let code = res.status;

            if (code === 200) {
                let data = res.data;
                let totalCount = data.total;
                let totalPage = data.pages;
                let page = data.pageNum;
                if (page === 1) {
                    paging(totalCount, totalPage, page);
                }
                console.log(res)

                let list = data.list;
                // paging(totalCount, totalPage, page);
                let htmlStr = '';
                for (var i = 0; i < list.length; i++) {
                    let dataJson = list[i];
                    let create_time = stampTOtime(dataJson.create_time);
                    let group_id = dataJson.group_id;
                    let group_name = dataJson.group_name;
                    let del_status = dataJson.del_status;
                    let btn = 'danger';
                    let stat = '禁用';
                    if (del_status === 0) {
                        status = '已启用';
                    } else {
                        status = '已禁用';
                        btn = 'primary';
                        stat = '启用';
                    }
                    let groupId = 'groupId_'+dataJson.group_id;

                    htmlStr = '<tr class="textAlign">' +
                        '<td data-id="' + group_id + '">' + group_name + '</td>' +
                        /*'<td data-code="' + organization_code + '" data-id="' + organization_id + '">' + organization_short + '</td>' +*/
                        '<td>'+status+'</td>' +
                        '<td>' + create_time + '</td>' +
                        // '<td>' + project_type + '</td>' +
                        // '<td>' + status + '</td>' +
                        // '<td>' + update_ime + '</td>' +
                        /* '<td>' + create_time + '</td>' +*/
                        /*'<td>' + term_of_validity + '</td>' +*/
                        '<td>' +
                        '<div class="btn-group">' +
                        '<button class="btn-' + btn + ' btn btn-xs" onclick=\'changeState("' + group_id + '","' + dataJson.del_status + '")\'>' + stat + '</button>'
                        +'<button class="btn-info btn btn-xs" onclick=\'updateGroup('+dataJson.id+')\'>' + '修改' + '</button>'
                    '</div>' +
                    '</td>' +
                    '</tr>'
                    $("#defaultGroup").append(htmlStr);
                }




            } else {
                $("#defaultProject").html('<tr><td colspan="11">暂无数据！</td></tr>');
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
        // callback: function (page) {
        //     // 回调函数
        //     loading(page);
        // }
    });
}

function JumpToPage(pagenum) {
    loading(pagenum);
}

function changeState(groupId, status) {

    if (status == 1) {
        status = 0
    }else {
        status = 1
    }
    let object = new Object();
    object.groupId = groupId;
    object.status = status;

    $.ajax({
        type: "POST",
        url: "/defaultGroup/updateGroupStatus",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(object),
        success: function (data) {
            loading(1)
        },
    });
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







function updateGroup(groupId) {
    // groupId = groupId.replace('groupId_','');

    /*$.get("/user/getUser?oldTelephone="+telephone );*/

    location.href="/defaultGroup/updateGroupPage?groupId="+groupId;

    /*$.ajax({
        type: "get",
        url: "/user/getUser?oldTelephone="+telephone
    })*/

}

