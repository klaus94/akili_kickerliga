import android.util.Log
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RestService
{
	val BASE_URL = "http://192.168.178.43:5000/"


	private val api: API

	init
	{
		val retrofit = Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.build()

		api = retrofit.create(API::class.java)
	}

	fun getUsers(): List<User>
	{

		val response = api.getUsers().execute()
		if (response.isSuccessful)
		{
			return response.body()!!;
		}
		else
		{
			return ArrayList<User>()
		}

	}

	fun createUser(userName: String): Boolean
	{
		val request = api.createUser(User(userName, 0, ""))

		Log.i("test", request.toString())
		val response= request.execute()
		if (response.isSuccessful)
		{
			return response.body()!!;
		}
		else
		{
			return false
		}
	}
}