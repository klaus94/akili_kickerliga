import retrofit2.Call
import retrofit2.http.*

interface  API
{
	@POST("/login")
	fun login(@Body name: String): Call<User>

	@GET("/users")
	fun getUsers(): Call<List<User>>;

	@GET
	fun getUser(@Url url: String): Call<User>

	@POST("/users")
	fun createUser(@Body user: User): Call<Boolean>;
}