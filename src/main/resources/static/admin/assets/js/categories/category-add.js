$(document).ready(function() {
    $('#btnCreateCategory').click(function() {
        var myModal = new bootstrap.Modal(document.getElementById('addCategoryModal'), {
            keyboard: false
        });
        myModal.show();
    });
});

function addCategory(){
    var name = $('#newCategoryName').val();
    var age_accept = $('#newCategoryAgeAccept').val();
    console.log(name);
    console.log(age_accept);
    $.ajax({
        url: '/api/categories/set-category',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({name: name, age_accept: age_accept}),
        success: function (response){
            $('#addCategoryModal').modal('hide');
            $('example').DataTable().ajax.reload();
        },
        error: function (){
            alert("Error adding cinema");
        }
    });
}