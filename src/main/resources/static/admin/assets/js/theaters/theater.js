var cinemas = {};
$.getJSON('/api/cinemas/get-all-cinemas', function(response) {
    // Kiểm tra phản hồi từ API có thành công và có dữ liệu không
    if (response.httpStatus === "OK" && Array.isArray(response.data)) {
        // Duyệt qua mảng dữ liệu và gán tên danh mục vào đối tượng categories
        response.data.forEach(function(cinema) {
            cinemas[cinema.id] = cinema.name;
        });
        console.log("response: ",response.data)
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
    sAjaxSource: '/api/theaters/get-theaters',
    fnServerData: function(sSource, aoData, fnCallback) {
        aoData.push({"name": "name", "value": $('#searchInput').val()}); // Correctly captures the name
        $.getJSON(sSource, aoData, function(json) { fnCallback(json) });
    },
    columns: [
        { data: 'id' },
        { data: 'name' },
        { data: 'cinema_id', render: function(data, type, row) {
                return cinemas[data] || 'Unknown cinemas'; // Sử dụng dữ liệu đã tải để hiển thị tên danh mục
        }},
        { data: 'seating_capacity'},
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
});

function loadCinemas(id) {
    $.getJSON('/api/cinemas/get-all-cinemas', function(response) {
        if (response.httpStatus === "OK" && response.data) {
            var select = $(`#${id}`);// Đảm bảo ID này trùng với ID của thẻ select trong HTML
            select.empty(); // Xóa các lựa chọn cũ nếu có

            response.data.forEach(function(cinema) {
                select.append($('<option>', {
                    value: cinema.id,
                    text: cinema.name
                }));
            });
        } else {
            console.error('Failed to load categories');
        }
    });
}



