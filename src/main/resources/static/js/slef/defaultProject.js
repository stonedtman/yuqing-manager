$(function () {
    $('#head').html('默认方案管理')
    $('#navigation').html('默认方案管理')



    $.ajax({
        type: "get",
        url: "/defaultProject/getDefaultGroupList",
        contentType: "application/json;charset=UTF-8",
        success: function (response) {
            console.log(response);
            // let res = JSON.parse(response);

            if (response.status === 200) {
                let groupData = response.data;
                if (groupData != null) {
                    for (let i = 0; i < groupData.length; i++) {
                        let group = groupData[i];
                        $("#defaultGroup").append("<option value='" + group.group_id + "'>--" + group.group_name + "--</option>");
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

// function getProjectList() {
//     let groupId = $("#defaultGroup option:selected").val();
//     if (groupId == null || groupId == "") {
//         $("#defaultProjectList").html("<option value=\"\">--请选择--</option>")
//         //清空
//         $("#defaultProject").html('')
//         return;
//     }
//     $.ajax({
//         type: "get",
//         url: "/defaultProject/getDefaultProjectList?groupId=" + groupId,
//         contentType: "application/json;charset=UTF-8",
//         success: function (response) {
//             $("#defaultProjectList").html("<option value=\"\">--请选择--</option>")
//             let res = JSON.parse(response);
//             //装公司数
//             if (res.status === 200) {
//                 let projectData = res.data;
//                 if (projectData != null) {
//                     for (let i = 0; i < projectData.length; i++) {
//                         let project = projectData[i];
//                         $("#defaultProjectList").append("<option value='" + project.project_id + "'>--" + project.project_name + "--</option>");
//                     }
//                 }
//             }else {
//                 $("#defaultProjectList").html("<option value=\"\">--方案查询出错--</option>")
//             }
//
//         }
//     })
// }

function loading(pagenum) {

    let dataObject = new Object();
    let groupId = $("#defaultGroup option:selected").val();

    dataObject.page = pagenum;
    dataObject.groupId = groupId;

    $.ajax({
        type: "POST",
        url: "/defaultProject/getDefaultProjectList",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(dataObject),
        beforeSend: function () {
            $("#defaultProject").html('');
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
                    let project_id = dataJson.project_id;
                    let project_name = dataJson.project_name;
                    let subject_word = dataJson.subject_word;
                    let stop_word = dataJson.stop_word===""?"————————":dataJson.stop_word;
                    let update_ime = stampTOtime(dataJson.update_time);
                    let project_type = dataJson.project_type;
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

                    if (project_type === 2) {
                        project_type = '普通方案';
                    }else {
                        project_type = '高级方案';
                    }

                    htmlStr = '<tr class="textAlign">' +
                        '<td data-id="' + project_id + '">' + project_name + '</td>' +
                        /*'<td data-code="' + organization_code + '" data-id="' + organization_id + '">' + organization_short + '</td>' +*/
                        '<td>'+subject_word+'</td>' +
                        '<td>' + stop_word + '</td>' +
                        '<td>' + project_type + '</td>' +
                        '<td>' + status + '</td>' +
                        // '<td>' + update_ime + '</td>' +
                        /* '<td>' + create_time + '</td>' +*/
                        /*'<td>' + term_of_validity + '</td>' +*/
                        '<td>' +
                        '<div class="btn-group">' +
                        '<button class="btn-' + btn + ' btn btn-xs" onclick=\'changeState("' + project_id + '","' + dataJson.del_status + '")\'>' + stat + '</button>'
                        +'<button class="btn-info btn btn-xs" onclick=\'updateProject(project_id)\'>' + '修改' + '</button>'
                        '</div>' +
                        '</td>' +
                        '</tr>'
                    $("#defaultProject").append(htmlStr);
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

function changeState(projectId, status) {

    if (status == 1) {
        status = 0
    }else {
        status = 1
    }
    let object = new Object();
    object.projectId = projectId;
    object.status = status;

    $.ajax({
        type: "POST",
        url: "/defaultProject/updateProjectStatus",
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







function updateProject(projectId) {


    /*$.get("/user/getUser?oldTelephone="+telephone );*/

    location.href="/defaultProject/updateProject?projectId="+projectId;

    /*$.ajax({
        type: "get",
        url: "/user/getUser?oldTelephone="+telephone
    })*/

}

