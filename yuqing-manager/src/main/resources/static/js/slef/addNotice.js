function addNotice() {

    /* if ($('#organization_id').val() == '' || $('#organization_id').val() == null) {
         alert("机构公共id不能为空！");
         return
     }*/
    if ($('#status').val() == '3' || $('#status').val() == null) {
        alert("请选择公告状态！");
        return
    }
    if ($('#content').val() == '' || $('#content').val() == null) {
        alert("公告内容不能为空！");
        return
    }



        let content = $("#content").val();
        let status = $("#status option:selected").val();


        let dataJson = new Object();
        dataJson.content = content;
        dataJson.status = status;
        $.ajax({
            type: "POST",
            url: ctxPath + "notice/addNotice",
            dataType: 'json',
            data: JSON.stringify(dataJson),
            contentType: 'application/json;charset=utf-8',
            success: function (res) {
                let code = res.status;
                if (code === 200) {
                    alert("添加成功");
                    location.href = ctxPath + "notice/list";
                } else if (code === 500) {
                    alert("添加出错");
                } else {
                    alert("添加失败");
                }
            },
            error: function (xhr, ajaxOptions, thrownError) {
                if (xhr.status == 403) {
                    window.location.href = ctxPath + "login";
                }
            }
        });
    }