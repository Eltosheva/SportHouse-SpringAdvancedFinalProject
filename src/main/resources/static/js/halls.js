$(function () {

    $(".hallEdit").click(
        function () {
            var id = $(this).parent().data("id");
            $("#id").val(id);
            $("#placeName").val($("#name_" + id).text());
            $("#hallCity").val($("#city_" + id).text());
            $("#hallAddress").val($("#address_" + id).text());
            $("#hallWorkFrom").val($("#workFrom_" + id).text());
            $("#hallWorkTo").val($("#workTo_" + id).text());
            $("#hallPhone").val($("#phone_" + id).text());
            $("#hallDescription").val($("#description_" + id).val());
            $("#placeImageUrl").val($("#imageUrl_" + id).val());
            $("#isActive").val($("#status_" + id).val());
        });
});