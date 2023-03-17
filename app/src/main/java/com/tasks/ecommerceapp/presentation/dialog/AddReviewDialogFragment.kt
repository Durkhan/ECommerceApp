package com.tasks.ecommerceapp.presentation.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.tasks.ecommerceapp.common.listener.UploadImageCallback
import com.tasks.ecommerceapp.data.model.customer.orders.OrderReviewRequest
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

    @Inject
    lateinit var uploadImageCloudinaryUseCase: UploadImageCloudinaryUseCase

    private val viewModel: AddReviewViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogOrderReviewBinding.inflate(layoutInflater)

        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
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
    }

    private fun setReviewObserver() {
        viewModel.addReviewLiveData.observe(viewLifecycleOwner, Observer {
            dismiss()
        })
    }

    private fun uploadImages2Cloud(uri: Uri) {
        uploadImageCloudinaryUseCase.invoke(uri, requireContext(), object : UploadImageCallback {
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
            productData?.id,
            binding.etReview.text.toString(),
            productImagesCloudUrlsList
        )
        viewModel.submitReview(data)
    }

    private fun initSubmitBtnVisibility() {
        binding.etReview.doAfterTextChanged {
            binding.btnSubmit.isClickable = it.isNullOrEmpty()
        }
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


    fun setProduct(productData: Product, productImageUrl: String): DialogFragment {
        this.productData = productData
        this.productImageUrl = productImageUrl
        return this
    }
}