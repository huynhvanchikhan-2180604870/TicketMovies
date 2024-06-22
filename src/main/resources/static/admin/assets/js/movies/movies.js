var categories = {};
$.getJSON('/api/categories/get-all-categories', function(response) {
    // Kiểm tra phản hồi từ API có thành công và có dữ liệu không
    if (response.httpStatus === "OK" && Array.isArray(response.data)) {
        // Duyệt qua mảng dữ liệu và gán tên danh mục vào đối tượng categories
        response.data.forEach(function(category) {
            categories[category.id] = category.name;
        });
    } else {
        // Ghi log lỗi nếu dữ liệu không hợp lệ hoặc không có dữ liệu
        console.error('Invalid or no data');
    }
});

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
        { data: 'category_id', render: function(data, type, row) {
            return categories[data] || 'Unknown Category'; // Sử dụng dữ liệu đã tải để hiển thị tên danh mục
        }},
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


