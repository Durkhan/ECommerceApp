import com.google.gson.annotations.SerializedName

data class Product(

    @SerializedName("enabled") var enabled: Boolean? = null,
    @SerializedName("imageUrls") var imageUrls: List<String> = listOf(),
    @SerializedName("quantity") var quantity: Int? = null,
    @SerializedName("_id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("previousPrice") var previousPrice: Int? = null,
    @SerializedName("currentPrice") var currentPrice: Int? = null,
    @SerializedName("categories") var categories: String? = null,
    @SerializedName("someOtherFeature") var someOtherFeature: String? = null,
    @SerializedName("color") var color: String? = null,
    @SerializedName("size") var size: String? = null,
    @SerializedName("ram") var ram: String? = null,
    @SerializedName("weight") var weight: String? = null,
    @SerializedName("itemNo") var itemNo: String? = null,
    @SerializedName("__v") var _v: Int? = null,
    @SerializedName("date") var date: String? = null

)