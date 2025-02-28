package com.hotstuff.ui.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.imageview.ShapeableImageView
import com.hotstuff.R
import com.hotstuff.databinding.FragmentItemDetailBinding
import com.hotstuff.utils.DatabaseHelper
import com.hotstuff.utils.PreferenceHelper
import java.text.DecimalFormat

class ItemDetailFragment : Fragment() {
    private var _binding: FragmentItemDetailBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        val context = requireContext()
        val preferenceHelper = PreferenceHelper(requireContext())

        val name = view.findViewById<TextView>(R.id.item_detail_name_text)
        val category = view.findViewById<TextView>(R.id.item_detail_category_text)
        val room = view.findViewById<TextView>(R.id.item_detail_room_text)
        val description = view.findViewById<TextView>(R.id.item_detail_description_text)
        val make = view.findViewById<TextView>(R.id.item_detail_make_text)
        val value = view.findViewById<TextView>(R.id.item_detail_value_text)
        val quantity = view.findViewById<TextView>(R.id.item_detail_quantity_text)
        val image = view.findViewById<ShapeableImageView>(R.id.item_detail_image)
        val bundle = this.requireArguments()

        fun formatValue(number: Double): String? {
            val df = DecimalFormat("#,###,###.##")
            return df.format(number)
        }

        val valueNumeral = formatValue(bundle.getDouble("value")).toString()
        val currency = preferenceHelper.getStringPref(context.getString(R.string.key_currency))
        val currencyIcon = preferenceHelper.getCurrencyIcon(currency)

        name.text = bundle.getString("name")
        category.text = bundle.getString("category")
        room.text = bundle.getString("room")

        val quantityNumeral = bundle.getInt("quantity")
        if (quantityNumeral == 1) quantity.text = "$quantityNumeral item"
        else quantity.text = "$quantityNumeral items"

        value.text = "$currencyIcon $valueNumeral"
        when (bundle.getString("make")) {
            "", null -> make.text = getText(R.string.filler_unspecified)
            else -> make.text = bundle.getString("make")
        }
        when (bundle.getString("description")) {
            "", null -> description.text = getText(R.string.filler_unspecified)
            else -> description.text = bundle.getString("description")
        }

        val imageURI = bundle.getString("image")
        try {
            if (imageURI != null) image.setImageURI(imageURI.toUri())
        } catch (e: Exception) {
            e.printStackTrace()
            image.setImageResource(R.drawable.image_default_item)
            bundle.putString("image", null)
            DatabaseHelper(requireContext()).removeInvalidImageURI(bundle.getInt("id"))
        }

        val editButton = view.findViewById<MaterialButton>(R.id.item_detail_edit_button)
        editButton.setOnClickListener{
            childFragmentManager.beginTransaction().addToBackStack(null).commit()
            findNavController().navigate(R.id.action_item_detail_to_edit_item, bundle)
        }

        val deleteButton = view.findViewById<MaterialButton>(R.id.item_detail_delete_button)
        deleteButton.setOnClickListener{
            val alertDialogBuilder = MaterialAlertDialogBuilder(requireContext(), R.style.dialog_alert)
            alertDialogBuilder.setTitle(getString(R.string.dialog_delete_item_title))
            alertDialogBuilder.setMessage(getString(R.string.dialog_delete_item_message, name.text))
            alertDialogBuilder.setPositiveButton(getString(R.string.dialog_delete)) { dialog, _ ->
                DatabaseHelper(requireContext()).deleteItem(bundle.getInt("id"))
                bundle.putInt("delete", bundle.getInt("position"))
                findNavController().navigate(R.id.action_item_detail_to_items, bundle)
                dialog.dismiss()
            }
            alertDialogBuilder.setNegativeButton(getString(R.string.dialog_negative)) { dialog, _ ->
                dialog.dismiss()
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }

        return view
    }

}