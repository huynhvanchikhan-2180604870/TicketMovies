var gird;
gird = $('#example').DataTable({
    serverSide: true,
    processing: true,
    stateSave: true,
    filter:false,
    pageLength: 10,
    //paging:true,
    lengthChange: true,
    sAjaxSource: '/api/cinemas/get-cinemas',
    fnServerData: function(sSource, aoData, fnCallback) {
        aoData.push({"name": "name", "value": $('#searchInput').val()}); // Correctly captures the name
        $.getJSON(sSource, aoData, function(json) { fnCallback(json) });
    },
    columns: [
        { data: 'id' },
        { data: 'name' },
        { data: 'location', bSortable: false },
        { data: 'total_theaters', bSortable: false},
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


