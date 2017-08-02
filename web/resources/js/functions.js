$(document).ready(function () {
    $("#revista_info").hide();
    $("#congreso_info").hide();

    $(".tipopublicacion").change(function () {
        var tipoPub = $(".tipopublicacion").val();
        if (tipoPub !== "") {
            if (tipoPub === "revista") {
                $("#revista_info").show("fade", 600);
                $("#congreso_info").hide();
            } else {
                $("#revista_info").hide();
                $("#congreso_info").show("fade", 600);
            }
        } else {
            $("#revista_info").hide();
            $("#congreso_info").hide();
        }
    });
});
 
 
 