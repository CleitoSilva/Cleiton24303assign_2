package com.student24304.simplenavigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.student24304.simplenavigation.models.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class MovieViewModel : ViewModel() {

    /*It is not necessary to define the max Seats because it
      is already generated when instantiating the class*/
    val movies = listOf(
        Movie(
            id = 0,
            title = "GHOSTBUSTERS: FROZEN EMPIRE",
            posterResId = R.drawable.poster_ghostbusters,
            description = "In Ghostbusters: Frozen Empire, the Spengler family returns to where it all started – the iconic New York City firehouse – to team up with the original Ghostbusters, who’ve developed a top-secret research lab to take busting ghosts to the next level. But when the discovery of an ancient artifact unleashes an evil force, Ghostbusters new and old must join forces to protect their home and save the world from a second Ice Age.",
            runningTime = "1hr 56mins",
            starring = "Paul Rudd, Bill Murray, Finn Wolfhard, Dan Aykroud, Annie Potts, Mckenna Grace, Ernie Hudson"
        ), Movie(
            id = 1,
            title = "IMMACULATE",
            posterResId = R.drawable.poster_immaculate,
            description = "Cecilia, a woman of devout faith, is warmly welcomed to the picture-perfect Italian countryside where she is offered a new role at an illustrious convent. But it becomes clear to Cecilia that her new home harbors dark and horrifying secrets.",
            runningTime = "1hr 29mins",
            starring = "Sydney Sweeney, Benedetta Porcaroli, Álvaro Morte, Simona Tabasco"
        ), Movie(
            id = 2,
            title = "DUNE: PART TWO",
            posterResId = R.drawable.poster_dune_part_ii,
            description = "Paul Atreides unites with Chani and the Fremen while on a warpath of revenge against the conspirators who destroyed his family. Facing a choice between the love of his life and the fate of the known universe, he endeavors to prevent a terrible future only he can foresee.",
            runningTime = "2hr 46mins",
            starring = "Timothée Chalamet, Florence Pugh, Zendaya , Souheila Yacoub, Austin Butler"
        ), Movie(
            id = 3,
            title = "GODZILLA X KONG: THE NEW EMPIRE",
            posterResId = R.drawable.godzilla_x_gong_the_new_empire,
            description = "The epic battle continues! Legendary Pictures’ cinematic Monsterverse follows up the explosive showdown of “Godzilla vs. Kong” with an all-new adventure that pits the almighty Kong and the fearsome Godzilla against a colossal undiscovered threat hidden within our world, challenging their very existence—and our own. “Godzilla x Kong: The New Empire” delves further into the histories of these Titans and their origins, as well as the mysteries of Skull Island and beyond, while uncovering the mythic battle that helped forge these extraordinary beings and tied them to humankind forever.",
            runningTime = "1hr 55mins",
            starring = "Rebecca Hall, Dan Stevens, Brian Tyree Henry, Fala Chen, Kaylee Hottle, Alex Ferns"
        ),
        Movie(
            id = 4,
            title = "AADUJEEVITHAM - THE GOAT LIFE",
            posterResId = R.drawable.poster_aadujeevitham_malayalam,
            description = "The real-life incident of an Indian migrant worker, Najeeb Muhammad, who goes to Saudi Arabia to earn money. However, in a twist of fate, he finds himself living a slave-like existence, herding goats in the middle of the desert.",
            runningTime = "2hr 41mins",
            starring = "Prithviraj Sukumaran, Amala Paul, Jimmy Jean-Louis, Rik Aby, Ajesh Babu\n"
        ),
        Movie(
            id = 5,
            title = "THE FIRST OMEN",
            posterResId = R.drawable.poster_the_first_omen,
            description = "When a young American woman is sent to Rome to begin a life of service to the church, she encounters a darkness that causes her to question her own faith and uncovers a terrifying conspiracy that hopes to bring about the birth of evil incarnate.",
            runningTime = "1hr 59mins",
            starring = "Bill Nighy, Ralph Ineson, Sônia Braga, Nell Tiger Free, Tawfeek Barhom"
        ),
        Movie(
            id = 6,
            title = "MONKEY MAN",
            posterResId = R.drawable.poster_monkey_man,
            description = "scar® nominee Dev Patel (Lion, Slumdog Millionaire) achieves an astonishing, tour-de-force feature directing debut with an action thriller about one man’s quest for vengeance against the corrupt leaders who murdered his mother and continue to systemically victimize the poor and powerless. Inspired by the legend of Hanuman, an icon embodying strength and courage, Monkey Man stars Patel as Kid, an anonymous young man who ekes out a meager living in an underground fight club where, night after night, wearing a gorilla mask, he is beaten bloody by more popular fighters for cash. After years of suppressed rage, Kid discovers a way to infiltrate the enclave of the city’s sinister elite. As his childhood trauma boils over, his mysteriously scarred hands unleash an explosive campaign of retribution to settle the score with the men who took everything from him.",
            runningTime = "2hr 1min",
            starring = "Sharlto Copley, Dev Patel, Sobhita Dhulipala, Ashwini Kalsekar, Adithi Kalkunte, Sikandar Kher, Pitobash , Vipin Sharma, Makarand Deshpande"
        ),
        Movie(
            id = 7,
            title = "WONKA",
            posterResId = R.drawable.poster_wonka,
            description = "Armed with nothing but a hatful of dreams, young chocolatier Willy Wonka manages to change the world, one delectable bite at a time.",
            runningTime = "1hr 56mins",
            starring = "Timothée Chalamet, Olivia Colman, Rowan Atkinson, Keegan-Michael Key, Matt Lucas"
        ),
        Movie(
            id = 8,
            title = "MIGRATION",
            posterResId = R.drawable.poster_migration,
            description = "A family of ducks decides to leave the safety of a New England pond for an adventurous trip to Jamaica. However, their well-laid plans quickly go awry when they get lost and wind up in New York City. The experience soon inspires them to expand their horizons, open themselves up to new friends, and accomplish more than they ever thought possible.",
            runningTime = "1hr 30mins",
            starring = "Elizabeth Banks, Danny DeVito, Keegan-Michael Key, Kumail Nanjiani, Awkwafina"
        )
    )

    fun getMovieById(movieId: Int): Movie {
        return movies.find { it.id == movieId }!!
    }

    private val _searchText = MutableStateFlow("")
    private val searchText = _searchText.asStateFlow()
    private val _searchedMovies = MutableStateFlow(movies)
    val searchedMovies = searchText
        .combine(_searchedMovies){ text, movies ->
            when {
                text.isNotEmpty() -> movies.filter { movie ->
                    movie.title.contains(text.trim(), ignoreCase = true)
                }

                else -> emptyList()
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onSearchTextChange(text: String){
        _searchText.value = text
    }
}