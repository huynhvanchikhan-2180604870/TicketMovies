function clickDelete(id) {
    var table = $('#example').DataTable();
    var data = table.row(function(idx, data, node) {
        return data.id === id;  // Ensure you are comparing the right ID field
    }).data();
    console.log("DAY LA ID: ", id);
    if (data) {
        $('#deleteCategoryID').val(data.id);  // Store ID in hidden input
        $('#deleteModal').modal('show');
    } else {
        console.error('No data found for ID:', id);
    }
}

function deleteCategory(){
    var id = $('#deleteCategoryID').val(); // Retrieve the ID from hidden input
    $.ajax({
        url: '/api/categories/delete-category/' + id,
        type: 'DELETE',
        success: function (result) {
            $('#deleteModal').modal('hide');
            $('#example').DataTable().ajax.reload();
            console.log("Delete successful");
        },
        error: function (err) {
            console.error("Error deleting category: ", err);
            alert('Error deleting category.');
            $('#deleteModal').modal('hide');
        }
    });
}
