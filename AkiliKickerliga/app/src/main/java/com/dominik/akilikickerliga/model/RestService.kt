import android.util.Log
import com.dominik.akilikickerliga.adapter.API
import com.dominik.akilikickerliga.model.User
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RestService
{
	val BASE_URL = "http://192.168.178.20:5000/"


	private val api: API

	init
	{
		val retrofit = Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.build()

		api = retrofit.create(API::class.java)
	}

	fun login(userName: String): User?
	{
		val response = api.login(userName).execute()
		if (response.isSuccessful)
		{
			return response.body()!!
		}
		else
		{
			return null
		}
	}

	fun getUser(name: String): User?
	{
		val response = api.getUser("users/" + name).execute()
		if (response.isSuccessful)
		{
			Log.i("test", response.body().toString())
			return response.body()!!;
		}
		else
		{
			return null
		}
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