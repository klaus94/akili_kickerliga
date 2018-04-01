import com.google.gson.annotations.SerializedName

class User(
	@SerializedName("name") val name: String,
	@SerializedName("points") val points: Int,
	@SerializedName("imageURL") val imageURL: String)
{

}