
var gird;
gird = $('#example').DataTable({
    serverSide: true,
    processing: true,
    stateSave: true,
    filter:false,
    pageLength: 10,
    //paging:true,
    lengthChange: true,
    sAjaxSource: '/api/movies/get-movies',
    fnServerData: function(sSource, aoData, fnCallback) {
        aoData.push({"name": "name", "value": $('#searchInput').val()}); // Correctly captures the name
        $.getJSON(sSource, aoData, function(json) { fnCallback(json) });
    },
    columns: [
        { data: 'id' },
        { data: 'title' },
        { data: 'description'},
        { data: 'director'},
        { data: 'duration'},
        { data: 'authors'},
        { data: 'release_date', render: function(data, type, full) {
            if (data) {
                // Đặt locale tiếng Việt và định dạng ngày giờ
                return moment(data).locale('vi').format('DD-MMM-YYYY (HH:mm)');
            } else {
                return 'No release date';
            }
        }},
        { data: 'poster_url', render: function(data, type, full) {
            // Đảm bảo rằng đường dẫn ảnh có dữ liệu hợp lệ
            if (data) {
                return '<img src="' + data + '" style="width:100px; height:auto;">';
            } else {
                return 'No image available';
            }
        }},
        { data: 'category.name'},
        {
            mData: null, bSortable: false, mRender: function(data, type, full) {

                return `<a class="btn btn-outline-dark" onclick="clickEdit('${full.id}')" >Edit</a>
                            <a class="btn btn-outline-danger" onclick="clickDelete('${full.id}')" >Delete</a>`;

            }
        }
    ],

    scroller: {
        loadingIndicator: true
    }
});

// Trigger reload when the search box is used
$(document).ready(function() {
    $('#searchInput, #SearchMark').on('keyup', function() {
        grid.ajax.reload(null, false); // Use null to keep the current paging and false to not reset paging.
    });

    loadCategories();
});

function loadCategories(id) {
    $.getJSON('/api/categories/get-all-categories', function(response) {
        if (response.httpStatus === "OK" && response.data) {
            var select = $(`#${id}`);// Đảm bảo ID này trùng với ID của thẻ select trong HTML
            select.empty(); // Xóa các lựa chọn cũ nếu có

            response.data.forEach(function(category) {
                select.append($('<option>', {
                    value: category.id,
                    text: category.name
                }));
            });
        } else {
            console.error('Failed to load categories');
        }
    });
}


