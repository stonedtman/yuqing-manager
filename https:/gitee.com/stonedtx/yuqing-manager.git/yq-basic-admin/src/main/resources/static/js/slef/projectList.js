$(function () {
    $('#head').html('方案管理')
    $('#navigation').html('方案管理')
    loading(1);
})

$("#users").on('change',function () {

    $("#projectlist").html("");

    let userid = $("#users option:selected").val();

    $("#groups").html("<option value=\"\">--请选择被拷贝的方案组--</option>")

    if (userid != null && userid != '') {
        $.ajax({
            type: "get",
            url: "/project/getGroupListByuserId?userId=" + userid,
            success: function (res) {


                if (res.code == 200){

                    let data = res.data;

                    for (let i = 0; i < data.length; i++){
                        let group = data[i];

                        let group_id = group.group_id;
                        let group_name = group.group_name;
                        if (group_name != null && group_name!= '' && group_id != null && group_id != ''){
                            $("#groups").append('<option value="'+group_id+'">'+group_name+'</option>');
                        }

                    }

                    loading(1);

                }else if (res.code == 0){
                    alert("未查询到项目组")
                }else {
                    alert('项目组查询出错')
                }

            }
        })
    }else{
        /*alert("请选择被拷贝的用户");*/
        loading(1);
        return;
    }
})


$("#groups").on('change',function () {



    $("#projectlist").html("");

    loading(1);


})


function loading(pagenum) {

    let datas = new Object();
    let groupid = $("#groups option:selected").val();
    let userId = $("#users option:selected").val();
    datas.group_id = groupid;
    datas.user_id = userId;
    datas.page = pagenum;

    if (groupid != null && groupid != '') {

        $.ajax({
            type: "POST",
            url: "/project/getProjectListByGroupId",
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(datas),
            beforeSend: function () {
                $("#projectlist").html('');
            },
            compvare: function () {
            },
            success: function (res) {
                install(res);
            },
        });
    }else if (userId != null && userId != ''){
        $.ajax({
            type: "POST",
            url: "/project/getProjectListByUserId",
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(datas),
            beforeSend: function () {
                $("#projectlist").html('');
            },
            compvare: function () {
            },
            success: function (res) {
                install(res);
            },
        });
    }else{
        $.ajax({
            type: "POST",
            url: "/project/getProjectList",
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(datas),
            beforeSend: function () {
                $("#projectlist").html('');
            },
            compvare: function () {
            },
            success: function (res) {
                install(res);
            },
        });
    }
}


function install(res) {
    var code = res.code;
    var totalCount = res.data.total;
    var totalPage = res.data.pages;
    var page = res.data.pageNum;
    if (page == 1) {
        paging(totalCount, totalPage, page);
    }
    if (code == 200) {
        console.log(res)
        var data = res.data.list;
        // paging(totalCount, totalPage, page);
        var htmlStr = '';

        /* var userHtml = '';
         for (var i = 0; i < userList.length; i++){
             var user = userList[i];
             userHtml += '<option value="'+user.user_id+'">'+user.username+'</option>';
         }*/

        for (var i = 0; i < data.length; i++) {
            var dataJson = data[i];

            let project_id =dataJson.project_id;

            let project_name = dataJson.project_name;
            if (project_name == null || project_name == ''){
                project_name = "———————";
            }else if (project_name.length > 20){
                project_name = project_name.substring(0 , 20) + "......";
            }

            let project_type = dataJson.project_type;
            if (project_type != null && project_type != '') {
                if (project_type == 1) {
                    project_type = '普通';
                } else {
                    project_type = '高级';
                }
            }


            let project_description = dataJson.project_description;
            if (project_description == null || project_description == '') {
                project_description = "———————";
            }else if (project_description.length > 20){
                project_description = project_description.substring(0 , 20) + "......";
            }

            let subject_word = dataJson.subject_word;
            if (subject_word == null || subject_word == '') {
                subject_word = "———————";
            }else if (subject_word.length > 20){
                subject_word = subject_word.substring(0 , 20) + "......";
            }

            let character_word = dataJson.character_word;
            if (character_word == null || character_word == '') {
                character_word = "———————";
            }else if (character_word.length > 20){
                character_word = character_word.substring(0 , 20) + "......";
            }

            let event_word = dataJson.event_word;
            if (event_word == null || event_word == '') {
                event_word = "———————";
            }else if (event_word.length > 20){
                event_word = event_word.substring(0 , 20) + "......";
            }

            let regional_word = dataJson.regional_word;
            if (regional_word == null || regional_word == '') {
                regional_word = "———————";
            }else if (regional_word.length > 20){
                regional_word = regional_word.substring(0 , 20) + "......";
            }

            let stop_word = dataJson.stop_word;
            if (stop_word == null || stop_word == '') {
                stop_word = "———————";
            }else if (stop_word.length > 20){
                stop_word = stop_word.substring(0 , 20) + "......";
            }






            htmlStr = '<tr class="textAlign">' +
                /*'<td>' + organization_id + '</td>' +*/
                '<td>' + project_name + '</td>' +
                '<td>' + project_type + '</td>' +
                '<td>' + project_description + '</td>' +
                '<td>' + subject_word + '</td>' +
                '<td>' + character_word + '</td>' +
                '<td>' + event_word + '</td>' +
                '<td>' + regional_word + '</td>' +
                '<td>' + stop_word + '</td>' +
                '<td>' +
                '<div class="btn-group">' +
                '<button class="btn-info btn btn-xs"  onclick="copy('+'\''+project_id+'\''+')">' + '拷贝' + '</button>' +
                '</div>' +
                '</td>' +
                '</tr>'
            $("#projectlist").append(htmlStr);





            /* $("#"+project_id).append(userHtml);
             $("#"+project_id).select2();*/

        }
    } else {
        $("#projectlist").html('<tr><td colspan="11">暂无数据！</td></tr>');
    }
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


function copy(project_id) {
    project_id = project_id.toString();
    //目标usesrId
    let user_id = $("#toUser option:selected").val();

    if (user_id == '' || user_id == null){
        alert("请选择拷贝至哪位客户");
        return;
    }

    let data = new Object();
    //方案组id
    /*let group_id = $("#groups option:selected").val();*/
    data.project_id = project_id;
    //需要复制的客户id
    data.user_id = user_id;
    /*data.group_id = group_id;*/

    $.ajax({
        type: "post",
        url: "/project/copy",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(data),
        success: function (res) {

            let code = res.code;

            if (code == 200){
                alert("拷贝成功");
            }else{
                alert("拷贝失败");
            }
        },
        error: function (res) {
            alert("出错了")
        }
    })
}

