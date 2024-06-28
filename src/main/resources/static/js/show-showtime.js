async function ShowTimeInBox() {
    try {
        const response = await fetch("/api/show-times/get-showtimes");
        if (!response.ok) {
            throw new Error('Network response was not ok ' + response.statusText);
        }

        const jsonResponse = await response.json();
        if (!jsonResponse.data) {
            throw new Error("JSON response does not contain 'data' field");
        }

        const showtimes = jsonResponse.data;
        const moviesPromises = showtimes.map(show => getMovieById(show.movie_id));
        const movies = await Promise.all(moviesPromises);

        let stringHTML = "";
        movies.forEach((movie, index) => {
            let movieShowtimes = showtimes.filter(st => st.movie_id === movie.id);

            // Format showtimes into a string with "3D" labels and full date in Vietnamese
            let timesHtml = movieShowtimes.map(st => {
                let startTime = new Date(st.startTime);
                let formattedDate = startTime.toLocaleDateString('vi-VN', {
                    weekday: 'long',
                    year: 'numeric',
                    month: 'long',
                    day: 'numeric'
                });
                let formattedTime = `${startTime.getHours()}:${startTime.getMinutes().toString().padStart(2, '0')}`;
                return `<span>${formattedDate}, ${formattedTime}<span class="d3">3D</span></span>`;
            }).join(' ');

            stringHTML += `
                <div class="card">
                    <img src="${movie.poster_url}" class="card-img-top" alt="Movie Poster">
                    <div class="card-body">
                        <h3 class="card-title">${movie.title}</h3>
                        <p class="time" style="font-size: 12px; margin-top: 5px">${timesHtml}</p>
                    </div>
                </div>
               
            `;
        });

        document.getElementById("box").innerHTML = stringHTML;
    } catch (error) {
        console.error('Error fetching showtimes:', error);
        alert("Failed to fetch showtimes: " + error.message);
    }
}


async function getMovieById(id) {
    try {
        const response = await fetch("/api/movies/get-movie-byid/" + id);
        console.log('Response:', response); // Debugging

        if (!response.ok) {
            throw new Error('Network response was not ok: ' + response.statusText);
        }

        const contentType = response.headers.get("content-type");
        if (!contentType || !contentType.includes("application/json")) {
            throw new TypeError("Expected JSON response but got: " + contentType);
        }

        const jsonResponse = await response.json();
        console.log('Raw JSON Response:', jsonResponse); // Debugging

        if (!jsonResponse.data) {
            throw new Error("JSON response does not contain 'data' field");
        }

        const movie = jsonResponse.data;
        console.log('Movie Data:', movie);
        return movie;
    } catch (error) {
        console.error('Error fetching movie by ID:', error);
        throw error; // Rethrow after logging for further handling upstream
    }
}

document.addEventListener('DOMContentLoaded', () => {
    ShowTimeInBox();
});
