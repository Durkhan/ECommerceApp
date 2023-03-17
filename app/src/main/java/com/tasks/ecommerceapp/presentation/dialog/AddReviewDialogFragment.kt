package com.tasks.ecommerceapp.presentation.dialog


import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.tasks.ecommerceapp.R
import com.tasks.ecommerceapp.common.EmptyTextWatcher
import com.tasks.ecommerceapp.common.ProductsResults
import com.tasks.ecommerceapp.common.isName
import com.tasks.ecommerceapp.common.listener.CompleteOrderListener
import com.tasks.ecommerceapp.common.listener.UploadImageCallback
import com.tasks.ecommerceapp.data.model.customer.orders.Order
import com.tasks.ecommerceapp.data.model.customer.review.OrderReviewRequest
import com.tasks.ecommerceapp.data.model.customer.review.Product
import com.tasks.ecommerceapp.databinding.DialogOrderReviewBinding
import com.tasks.ecommerceapp.domain.usecases.UploadImageCloudinaryUseCase
import com.tasks.ecommerceapp.presentation.adapter.ProductImagesAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddReviewDialogFragment : DialogFragment() {

    private lateinit var binding: DialogOrderReviewBinding
    private var productImagesAdapter = ProductImagesAdapter()
    private var pickImageLauncher: ActivityResultLauncher<String>? = null
    private var productData: Product? = null
    private var productImageUrl: String? = null
    private var productImagesCloudUrlsList = mutableListOf<String>()

    private var productImagesCounter = 0
    private var productImagesList = mutableListOf<Uri>()
    private val args:AddReviewDialogFragmentArgs by navArgs()

    @Inject
    lateinit var uploadImageCloudinaryUseCase: UploadImageCloudinaryUseCase

    private val viewModel: AddReviewViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogOrderReviewBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvProductImages.adapter = productImagesAdapter

        setProductInfo()
        setReviewObserver()
        initImagePicker()
        initClickEvents()
        initSubmitBtnVisibility()
        assignDataOfProduct()

    }
    @SuppressLint("SetTextI18n")
    private fun assignDataOfProduct() {
        val product=args.product
        with(binding){
            Glide.with(requireContext())
                .load(product?.productsItem?.product?.imageUrls?.get(0))
                .into(ivProduct)
            tvName.text=product?.productsItem?.product?.name
            tvPrice.text="US $${product?.productsItem?.product?.currentPrice}"
        }

    }
    private fun setReviewObserver() {
        viewModel.addReviewLiveData.observe(viewLifecycleOwner, Observer {
            dismiss()
        })
    }

    private fun uploadImages2Cloud(uri: Uri) {
        uploadImageCloudinaryUseCase(uri, requireContext(), object : UploadImageCallback {
            override fun onUploadSuccess(url: String) {
                productImagesCloudUrlsList.add(url)
                productImagesCounter++
                if (productImagesCounter < productImagesList.size) {
                    uploadImages2Cloud(productImagesList[productImagesCounter])
                } else {
                    prepareDataForSubmit()
                }
            }
        })
    }

    private fun prepareDataForSubmit() {
        val data = OrderReviewRequest(
            args.product?.customerId.toString(),
            args.product?.product.toString(),
            binding.etReview.text.toString(),
            productImagesCloudUrlsList
        )
        viewModel.submitReview(data)

        viewModel.addReviewLiveData.observe(viewLifecycleOwner){result ->
            when(result){
                is ProductsResults.Success -> {
                    dismiss()
                }
                else -> {}
            }

        }
    }

    private fun initSubmitBtnVisibility() {
        binding.etReview.addTextChangedListener(object :EmptyTextWatcher() {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().isNotBlank()){
                    binding.btnSubmit.isEnabled = true
                    binding.btnSubmit.backgroundTintList=ContextCompat.getColorStateList(requireContext(),
                        R.color.button_tint_enabled
                    )
                }else{
                    binding.btnSubmit.isEnabled = false
                    binding.btnSubmit.backgroundTintList=ContextCompat.getColorStateList(requireContext(),
                        R.color.button_is_not_enabled
                    )
                }

            }

        })
    }

    private fun initClickEvents() {
        binding.btnCancel.setOnClickListener {
            productData = null
            productImageUrl = null
            dismiss()
        }

        binding.ivAddPhoto.setOnClickListener {
            pickImageLauncher?.launch("image/*")
        }

        binding.btnSubmit.setOnClickListener {
            productImagesCounter = 0
            productImagesList.clear()
            productImagesList.addAll(productImagesAdapter.getProductImages())

            if (productImagesList.isNotEmpty()) {
                uploadImages2Cloud(productImagesList[productImagesCounter])
            } else {
                prepareDataForSubmit()
            }
        }
    }

    private fun initImagePicker() {
        pickImageLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                if (uri != null) {
                    productImagesAdapter.updateList(uri)
                }
            }
    }

    private fun setProductInfo() = with(binding) {
        if (productData == null) {
            return@with
        }
        tvName.text = productData?.name
        tvPrice.text = productData?.currentPrice?.toString()

        if (!productImageUrl.isNullOrEmpty()) {
            Glide.with(requireContext()).load(Uri.parse(productImageUrl)).into(ivProduct)
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        val back = ColorDrawable(Color.TRANSPARENT)
        val margin = 50
        val inset = InsetDrawable(back, margin)
        dialog?.window?.setBackgroundDrawable(inset)
    }

}