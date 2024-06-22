function clickEdit(id) {
    loadCategories('CategorySelect');
    var table = $('#example').DataTable();
    var data = table.row(function(idx, data, node) {
        return data.id === id;
    }).data();
    console.log("DAY LA ID: ", id);
    if (data) {
        $('#MovieID').val(data.id);
        $('#MovieTitle').val(data.title);
        $('#MovieDescription').val(data.description);
        $('#MovieDirector').val(data.director);
        $('#MovieDuration').val(data.duration);
        $('#MovieAuthors').val(data.authors);
        // Assuming the release_date might be in ISO format like '2024-06-07T00:00:00Z'
        var formattedDate = data.release_date.split('T')[0]; // Extract 'YYYY-MM-DD' part
        $('#MovieReleaseDate').val(formattedDate);
        $('#MoviePoster').val(data.poster_url);
        $('#CategorySelect').val(data.category_id);
        $('#editMovieModal').modal('show');
    } else {
        console.error('No data found for ID:', id);
    }
}

function saveMovie(){
    var id = $('#MovieID').val();
    var title = $('#MovieTitle').val();
    var description = $('#MovieDescription').val();
    var director = $('#MovieDirector').val();
    var duration = $('#MovieDuration').val();
    var authors = $('#MovieAuthors').val();
    var release_date = $('#MovieReleaseDate').val();
    var category_id = $('#CategorySelect').val();
    var poster_url = $('#MoviePoster').val();

    console.log("Saving Movie:", {id, title, description, director, release_date, category_id, poster_url}); // Log all data

    $.ajax({
        url: '/api/movies/update-movie/' + id,
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
        success: function(response) {
            $('#editMovieModal').modal('hide');
            $('#example').DataTable().ajax.reload();
            console.log("Update successful");
        },
        error: function(xhr, status, error) {
            console.error("Update failed:", xhr.responseText);
            alert('Error updating movie: ' + xhr.responseText);
        }
    });
}


