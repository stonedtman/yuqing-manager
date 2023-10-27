$(function () {
    $('#head').html('用户管理')
    $('#navigation').html('用户管理')



    $.ajax({
        type: "get",
        url: "/defaultProject/getDefaultGroupList",
        contentType: "application/json;charset=UTF-8",
        success: function (response) {
            let res = JSON.parse(response);
            //装公司数
            if (res.status === 200) {
                let groupData = res.data;
                if (groupData != null) {
                    for (let i = 0; i < groupData.length; i++) {
                        let group = groupData[i];
                        $("#defaultGroup").append("<option value='" + group.group_id + "'>--" + group_name + "--</option>");
                    }
                    loading(1);
                }
            }else {
                $("#company").html("<option value=\"\">--方案组查询出错--</option>")
                loading(1);
            }

        }
    })


})

function getProjectList() {
    let groupId = $("#defaultGroup option:selected").val();
    if (groupId == null || groupId == "") {
        $("#defaultProjectList").html("<option value=\"\">--请选择--</option>")
        //清空
        $("#defaultProject").html('')
        return;
    }
    $.ajax({
        type: "get",
        url: "/defaultProject/getDefaultProjectList?groupId=" + groupId,
        contentType: "application/json;charset=UTF-8",
        success: function (response) {
            $("#defaultProjectList").html("<option value=\"\">--请选择--</option>")
            let res = JSON.parse(response);
            //装公司数
            if (res.status === 200) {
                let projectData = res.data;
                if (projectData != null) {
                    for (let i = 0; i < projectData.length; i++) {
                        let project = projectData[i];
                        $("#defaultProjectList").append("<option value='" + project.project_id + "'>--" + project.project_name + "--</option>");
                    }
                }
            }else {
                $("#defaultProjectList").html("<option value=\"\">--方案查询出错--</option>")
            }

        }
    })
}

function loading(pagenum) {

    let dataObject = new Object();
    let groupId = $("#defaultGroup option:selected").val();

    dataObject.page = pagenum;
    dataObject.groupId = groupId;

    $.ajax({
        type: "POST",
        url: ctxPath + "defaultProject/getDefaultProjectList",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(dataObject),
        beforeSend: function () {
            $("#defaultProjectList").html('');
        },
        complete: function () {
        },
        success: function (res) {
            let code = res.status;
            let totalCount = res.totalCount;
            let totalPage = res.totalPage;
            let page = res.page;
            if (page === 1) {
                paging(totalCount, totalPage, page);
            }
            if (code === 200) {
                console.log(res)
                let data = res.data;
                // paging(totalCount, totalPage, page);
                let htmlStr = '';
                for (var i = 0; i < data.length; i++) {
                    let dataJson = data[i];
                    let create_time = stampTOtime(dataJson.create_time);
                    let project_id = dataJson.project_id;
                    let project_name = dataJson.project_name;
                    let update_ime = stampTOtime(dataJson.update_time);
                    let project_type = dataJson.project_type;

                    let btn = 'danger';
                    let stat = '禁用';
                    if (status == 1) {
                        status = '已启用';
                    } else {
                        status = '已禁用';
                        btn = 'primary';
                        stat = '启用';
                    }

                    htmlStr = '<tr class="textAlign">' +
                        '<td>' + telephone + '</td>' +
                        '<td>' + username + '</td>' +
                        '<td data-code="' + organization_code + '" data-id="' + organization_id + '">' + organization_name + '</td>' +
                        /*'<td data-code="' + organization_code + '" data-id="' + organization_id + '">' + organization_short + '</td>' +*/
                        '<td>'+system_title+'</td>' +
                        '<td>' + status + '</td>' +
                        /* '<td>' + create_time + '</td>' +*/
                        '<td>' + login_count + '</td>' +
                        '<td>' + end_login_time + '</td>' +
                        /*'<td>' + term_of_validity + '</td>' +*/
                        '<td>' +
                        '<div class="btn-group">' +
                        '<button class="btn-' + btn + ' btn btn-xs" onclick=\'changeState("' + telephone + '","' + dataJson.status + '")\'>' + stat + '</button>'
                        +'<button class="btn-info btn btn-xs" onclick=\'updateUser("' + telephone + '","' + status + '")\'>' + '修改' + '</button>'
                        +'<button class="label-warning btn btn-xs" onclick=\'resetPasswords("' + telephone + '","' + 123456 + '")\'>' + '重置密码' + '</button>' +
                        '</div>' +
                        '</td>' +
                        '</tr>'
                    $("#userlist").append(htmlStr);
                }




            } else {
                $("#userlist").html('<tr><td colspan="11">暂无数据！</td></tr>');
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
            loading(page);
        }
    });
}

function JumpToPage(pagenum) {
    loading(pagenum);
}

function changeState(username, st) {
    let status = 1
    if (st == 1) {
        status = 0
    }
    let object = new Object();
    object.telephone = username;
    object.status = status;

    $.ajax({
        type: "POST",
        url: "/user/updateUserState",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(object),
        success: function (data) {
            if (data.code == 1 && st == '1') {
                alert('此用户已禁止登录')
            } else if (data.code == 1 && st == '0') {
                alert('此用户已启用登录')
            }
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







function updateUser(telephone , status){


    /*$.get("/user/getUser?oldTelephone="+telephone );*/

    location.href="/user/updateUser?oldTelephone="+telephone;

    /*$.ajax({
        type: "get",
        url: "/user/getUser?oldTelephone="+telephone
    })*/

}

function resetPasswords(telephone , password) {

    var b = confirm("确定重置密码吗？");

    if (b) {
        let object = new Object();
        object.telephone = telephone;
        object.password = password;

        $.ajax({
            type: "POST",
            url: "/user/updatePassword",
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            data: JSON.stringify(object),
            success: function (data) {
                if (data.code == 200) {
                    alert('已重置密码');
                } else if (data.code == 0) {
                    alert('密码未改变');
                }else if (data.code == 500){
                    alert('出现错误');
                }
                loading(1)

            },
        });
    }

}




//搜索事件
$("#keyword").keydown(function (res) {
    if (res.keyCode == 13)
        loading(1);
})
/*
$('#searchButton').click(function () {
    loading(1);
})*/


//选择企业事件
$("#company").on('change' , function () {
    loading(1);
})
