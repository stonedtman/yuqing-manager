function registerCompany() {

   /* if ($('#organization_id').val() == '' || $('#organization_id').val() == null) {
        alert("机构公共id不能为空！");
        return
    }*/
    if ($('#organization_code').val() == '' || $('#organization_code').val() == null) {
        alert("组织代码不能为空！");
        return
    }
    if ($('#organization_type').val() == '0' || $('#system_title').val() == null) {
        alert("请选择机构类型！");
        return
    } if ($('#status').val() == '0' || $('#system_title').val() == null) {
        alert("请选择机构状态！");
        return
    }
    if ($('#institution').val() == '' || $('#institution').val() == null) {
        alert("机构全称不能为空！");
        return
    }
    if ($('#institution_short').val() == '' || $('#institution_short').val() == null) {
        alert("机构简称不能为空！");
        return
    }
    if ($('#term_of_validity').val() == '' || $('#term_of_validity').val() == null) {
        alert("有效期不能为空！");
        return
    }
    if ($('#system_title').val() == '' || $('#system_title').val() == null) {
        alert("系统名称不能为空！");
        return
    }






    let id = $("#thisId").val();
    let organization_name = $("#institution").val();
    let organization_short = $("#institution_short").val();
    let term_of_validity = $("#term_of_validity").val();
    let system_title = $("#system_title").val();
   /* let organization_id = $("#organization_id").val();*/
    let organization_code = $("#organization_code").val();

    let organization_type = $("#organization_type option:selected").val();
    let status = $("#status option:selected").val();


    let dataJson = new Object();
    if ($("#logo_url").val() != '' && $("#logo_url").val() != null){
        let logo_url = $("#logo_url").val();
        dataJson.logo_url = logo_url;
    }
    dataJson.organization_name = organization_name;
    dataJson.organization_short = organization_short;
    dataJson.term_of_validity = term_of_validity;
    dataJson.system_title = system_title;
   /* dataJson.organization_id = organization_id;*/
    dataJson.organization_code = organization_code;
    dataJson.organization_type = organization_type;
    dataJson.status = status;
    dataJson.id = id;


    updateCompany(JSON.stringify(dataJson));
}

function updateCompany(data) {
    $.ajax({
        type: "POST",
        url: ctxPath + "company/updateCompanyByOrganizationId",
        dataType: 'json',
        data: data,
        contentType: 'application/json;charset=utf-8',
        success: function (res) {
            let code = res.code;
            if (code == 200) {
                alert("修改成功");
                location.href = ctxPath + "company/list";
            } else if(code == 500){
                alert("修改出错");
            }else{
                alert("没有修改");
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            if (xhr.status == 403) {
                window.location.href = ctxPath + "login";
            }
        }
    });
    // $.ajax({
    //     type: "POST",
    //     url: ctxPath + "user/addUserInfo",
    //     dataType: "json",
    //     contentType: 'application/json;charset=utf-8',
    //     data: data,
    //     success: function (res) {
    //         console.log(res)
    //         // if (data.code == 1) {
    //         //     alert('添加成功！')
    //         //     location.href = "list";
    //         // } else {
    //         //     alert('添加失败！')
    //         // }
    //     },
    //     error: function (err) {
    //         console.log(err);
    //     }
    // });
}