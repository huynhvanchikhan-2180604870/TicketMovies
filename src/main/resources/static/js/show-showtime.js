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
            stringHTML += `
                <div class="card">
                    <img src="${movie.poster_url}">
                    <div class="card-content">
                        <p class="movie-name">${movie.title}</p>
                        <div class="movie-info">
                            <p class="time">11:30 <span>14:45<span class="d3">3D</span> 16:05<span class="d3">3D</span></span> 18:40 21:00 23:15</p>
                        </div>
                    </div>
                </div>
            `;
        });
        document.getElementById("box").innerHTML = stringHTML;
    } catch (error) {
        console.error('Error fetching showstime:', error);
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
