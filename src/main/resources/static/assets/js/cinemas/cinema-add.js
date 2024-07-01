$(document).ready(function() {
    $('#btnCreateCinema').click(function() {
        var myModal = new bootstrap.Modal(document.getElementById('addCinemaModal'), {
            keyboard: false
        });
        myModal.show();
    });
});

function addCinema(){
    var name = $('#newCinemaName').val();
    var location = $('#newCinemaLocation').val();
    var total_theaters = $('#newCinemaTotolTheaters').val();
    console.log(name);
    console.log(location);
    console.log(total_theaters);
    $.ajax({
        url: '/api/cinemas/set-cinema',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({name: name, location: location, total_theaters: total_theaters}),
        success: function (response){
            $('#addCinemaModal').modal('hide');
            $('example').DataTable().ajax.reload();
        },
        error: function (){
            alert("Error adding cinema");
        }
    });
}