$(function () {
    // update role
    $(".rolesEdit").click(
        function () {
            var userId = $(this).parent().data("id");
            $("#userId").val(userId);
            $("#myModalLabel").html("Changing " + $("#firstName_" + userId).text()
                + " " + $("#lastName_" + userId).text() + " role:");
            $("#myModal").modal("show");
        });

    $("#save").click(
        function() {
            $.ajax({
                url: "/api/changeUserRole",
                type: "POST",
                data:  $('#mainForm').serialize(),
                success: function(res) {
                    if (res.valid) {
                        alert("success!");
                        location.reload();
                    } else {
                        alert(res.msg);
                    }
                }
            });
        });
})