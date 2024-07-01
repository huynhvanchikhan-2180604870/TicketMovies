function clickEdit(id) {
    var table = $('#example').DataTable();
    var data = table.row(function(idx, data, node) {
        return data.id === id;
    }).data();
    console.log("DAY LA ID: ", id);
    if (data) {
        $('#CategoryID').val(data.id);
        $('#CategoryName').val(data.name);
        $('#CategoryAgeAccept').val(data.age_accept);
        $('#editCategoryModal').modal('show');
    } else {
        console.error('No data found for ID:', id);
    }
}

function saveCategory(){
    var id = $('#CategoryID').val();
    console.log("Sending ID:", id); // Debugging to check if ID is null
    var name = $('#CategoryName').val();
    var age_accept = $('#CategoryAgeAccept').val();
    $.ajax({
        url: '/api/categories/update-category/' + id,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({ name: name, age_accept: age_accept}),
        success: function(response) {
            $('#editCinemaModal').modal('hide');
            $('#example').DataTable().ajax.reload();
            console.log("Update successful");
        },
        error: function(xhr, status, error) {
            console.error("Update failed:", xhr.responseText);
            alert('Error updating cinema: ' + xhr.responseText);
        }
    });
}