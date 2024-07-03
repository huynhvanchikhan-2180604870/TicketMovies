console.log("DOM fully loaded and parsed");

console.log("Document is ready");

let dataParameter = [];
const price = $(".price");

$(".slot__item").click(function () {
    console.log("Slot item clicked"); // Thêm log để kiểm tra

    const priceSeat = Number($(this).data("price"));
    const codeSeat = $(this).data("seat");
    const seatId = $(this).data("seat-id");

    console.log("Price seat:", priceSeat);
    console.log("Code seat:", codeSeat);
    console.log("Seat ID:", seatId);

    if (!$(this).hasClass("item--picked") && !$(this).hasClass("item--checked")) {
        if ($(this).hasClass("item--picking")) {
            $(this).removeClass("item--picking");
            updatePrice(priceSeat, false);
            removeSeatFromList(codeSeat);
            dataParameter.splice(dataParameter.indexOf(seatId), 1);
        } else {
            $(this).addClass("item--picking");
            updatePrice(priceSeat, true);
            addSeatToList(codeSeat);
            dataParameter.push(seatId);
        }
    }
});

function updatePrice(amount, isAdding) {
    let current = Number(price.data("price"));
    current = isAdding ? current + amount : current - amount;
    price.text(current.toLocaleString('en-US', {style: 'currency', currency: 'VND'}));
    price.data("price", current);
}

function addSeatToList(codeSeat) {
    $(".slot").append('<span class="mr-2">Ghế: ' + codeSeat + ',</span>');
}

function removeSeatFromList(codeSeat) {
    $(".mr-2").each(function () {
        if (this.textContent === "Ghế: " + codeSeat + ",") {
            $(this).remove();
        }
    });
}

const clock = setInterval(function () {
    const remaining = remainingTime();
    $('.time').text(remaining);
    if (remaining == null) {
        clearInterval(clock);
        $("#error-time").addClass("swal-overlay--show-modal");
    }
}, 1000);

function updateSeatStatus() {
    $(".item--picking").addClass("item--checked").removeClass("item--picking");
}

// function resetBill() {
//     $(".slot").empty();
//     price.data("price", 0);
//     price.text(0.toLocaleString('en-US', { style: 'currency', currency: 'VND' }));
// }

function showError(message) {
    $("#text-error").html(message);
    $("#error").addClass("swal-overlay--show-modal");
    $("button").click(function () {
        location.reload(true);
    });
}

$(".notify").click(function () {
    if (remainingTime() != null) {
        $(this).removeClass("swal-overlay--show-modal");
    }
});

$("#notify-error").click(function () {
    window.location.href = '/';
});



function loadUser(callback, username) {
    console.log("username: ",username)
    $.ajax({
        type: "GET",
        url: "/api/users/get-by-username/" + username,
        success: function (user) {
            console.log("User info:", user);
            callback(null, user);
        },
        error: function (error) {
            console.log("Error loading user info:", error);
            callback(error, null);
        }
    });
}

function getCurrentUsername(callback) {
    $.ajax({
        type: "GET",
        url: "/api/users/get-current-username",
        success: function (username) {
            console.log("Current username:", username);
            callback(null, username);
        },
        error: function (error) {
            console.log("Error getting current username:", error);
            callback(error, null);
        }
    });
}

$("#btn-booking").click(function () {
    if (dataParameter.length != 0) {
        getCurrentUsername(function (error, currentUsername) {
            if (error) {
                $("#text-error").html("Có lỗi xảy ra khi lấy username hiện tại.");
                $("#error").addClass("swal-overlay--show-modal");
                return;
            }

            console.log("Calling loadUser with username:", currentUsername);

            loadUser(function (error, user) {
                if (error) {
                    $("#text-error").html("Có lỗi xảy ra khi tải thông tin người dùng.");
                    $("#error").addClass("swal-overlay--show-modal");
                    return;
                }
                console.log(user);
                var username = user.username;
                var password = user.password; // Mật khẩu giả định được lấy từ phản hồi API
                var authHeader = "Basic " + btoa(username + ":" + password);

                var showtime_id = `${showtime_id}`;
                console.log("Showtime id: ", showtime_id);

                var json = JSON.stringify({
                    "user_id":user.id,
                    "showtime_id": showtime_id,
                    "list_seat_id": dataParameter
                });

                $.ajax({
                    type: "POST",
                    url: "/api/bills/create-new-bill",
                    headers: {"Authorization": authHeader},
                    data: json,
                    contentType: "application/json; charset=utf-8",
                    success: function (data) {
                        // Change status of seat
                        $(".item--picking").addClass("item--checked");
                        $(".item--picking").removeClass("item--picking");

                        // Reset bill
                        $(".slot").empty();
                        price.data("price", "0");
                        current = 0;
                        price.text(current.toLocaleString('en-US', {style: 'currency', currency: 'VND'}));

                        // Close all modal check-out
                        $('#CreditModal').trigger('click');

                        // Show notify
                        $("#success").addClass("swal-overlay--show-modal");

                        dataParameter = [];

                    },
                    error: function (e) {
                        $("#text-error").html(e.responseText);
                        $("#error").addClass("swal-overlay--show-modal");
                        $("button").click(function (e) {
                            location.reload(true);
                        });
                    }
                });
            }, currentUsername);
        });
    } else {
        $("#text-error").html("Bạn chưa chọn chỗ ngồi!");
        $("#error").addClass("swal-overlay--show-modal");
    }
});
