async function ShowTimeInBox() {
    try {
        const response = await fetch("/api/show-times/get-showtimes");
        if (!response.ok) {
            throw new Error('Network response was not ok: ' + response.status);
        }

        const jsonResponse = await response.json();
        if (!jsonResponse.data) {
            throw new Error("JSON response does not contain 'data' field");
        }

        const showtimes = jsonResponse.data;
        let stringHTML = "";
        showtimes.forEach((showtime, index) => {
            const startTime = new Date(showtime.startTime).toLocaleTimeString('vi-VN', { hour: '2-digit', minute: '2-digit' });
            const endTime = new Date(showtime.endTime).toLocaleTimeString('vi-VN', { hour: '2-digit', minute: '2-digit' });

            stringHTML += `
                <div class="card">
                    <img src="${showtime.movie.poster_url}" alt="Movie Poster">
                    <div class="card-content">
                        <p class="movie-name">${showtime.movie.title}</p>
                        <div class="movie-info">
                            <p class="time">${startTime} - ${showtime.endTime}</p>
                        </div>
                    </div>
                    <button class="detail-btn" onclick="showDetail('${showtime.id}')">Detail</button>
                </div>
            `;
        });
        document.getElementById("box").innerHTML = stringHTML;
    } catch (error) {
        console.error('Error fetching showtimes:', error);
        alert("Failed to fetch showtimes: " + error.message);
    }
}

function showDetail(showtimeId) {
    window.location.href = `/showtimes/show-detail/` + showtimeId;
}

document.addEventListener('DOMContentLoaded', () => {
    ShowTimeInBox();
});
