$(document).ready(function() {
    loadCategories('newCategorySelect');
    $('#btnCreateMovie').click(function() {
        var myModal = new bootstrap.Modal(document.getElementById('addMovieModal'), {
            keyboard: false
        });
        myModal.show();
    });
});

function addMovie(){
    var id = $('#newMovieID').val();
    var title = $('#newMovieTitle').val();
    var description = $('#newMovieDescription').val();
    var director = $('#newMovieDirector').val();
    var duration = $('#newMovieDuration').val();
    var authors = $('#newMovieAuthors').val();
    var release_date = $('#newMovieReleaseDate').val();
    var category_id = $('#newCategorySelect').val();
    var poster_url = $('#newMoviePoster').val();

    $.ajax({
        url: '/api/movies/set-movie',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            title: title,
            description: description,
            director: director,
            duration: duration,
            authors: authors,
            release_date: release_date,
            category_id: category_id,
            poster_url: poster_url
        }),
        success: function (response){
            $('#addCategoryModal').modal('hide');
            $('example').DataTable().ajax.reload();
        },
        error: function (){
            alert("Error adding cinema");
        }
    });
}