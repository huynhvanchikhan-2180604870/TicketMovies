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
    price.text(current.toLocaleString('en-US', { style: 'currency', currency: 'VND' }));
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

function remainingTime() {
    // const today = new Date();
    // // const dateSchedule = new Date([[${showtime.startTime}]] + ' ' + [[${showtime.startTime}]]);
    // const seconds = Math.floor((dateSchedule - today) / 1000);
    // if (seconds > 0) {
    //     const h = Math.floor(seconds / 3600);
    //     const m = Math.floor((seconds % 3600) / 60);
    //     const s = seconds - h * 3600 - m * 60;
    //     return `${h} giờ ${m} phút ${s} giây `;
    // }
    // return null;
}
