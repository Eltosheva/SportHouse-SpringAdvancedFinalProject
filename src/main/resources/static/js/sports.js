$(function () {

    $(".sportEdit").click(
        function () {
            var id = $(this).parent().data("id");
            $("#id").val(id);
            $("#sportName").val($("#name_" + id).text());
            $("#sportImageUrl").val($("#imageUrl_" + id).val());
            $("#sportDescription").val($("#description_" + id).val());
            $("#isActive").val($("#status_" + id).val());
        });
});