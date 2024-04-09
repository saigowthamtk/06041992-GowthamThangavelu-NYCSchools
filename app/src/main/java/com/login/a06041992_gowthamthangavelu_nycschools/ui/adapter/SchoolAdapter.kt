package com.login.a06041992_gowthamthangavelu_nycschools.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.login.a06041992_gowthamthangavelu_nycschools.R
import com.login.a06041992_gowthamthangavelu_nycschools.data.model.School
import com.login.a06041992_gowthamthangavelu_nycschools.databinding.ItemSchoolBinding

/**
 * Creating adapter to display the Schools
 */
class SchoolAdapter(
    private val schools: List<School>,
    private val onClick: (School) -> Unit
) : RecyclerView.Adapter<SchoolAdapter.SchoolViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolViewHolder {
        val binding = ItemSchoolBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SchoolViewHolder(binding).apply {
            //ClickListener to open SchooldetailsActivity based on position
            itemView.setOnClickListener {
                val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION } ?: return@setOnClickListener
                onClick(schools[position])
            }
        }
    }

    override fun onBindViewHolder(holder: SchoolViewHolder, position: Int) {
        holder.bind(schools[position])
    }

    override fun getItemCount(): Int = schools.size

    class SchoolViewHolder(
        private val binding: ItemSchoolBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        //Binding the values in XML
        fun bind(school: School) {
            with(binding) {
                schoolName.text = school.school_name
                schoolOverview.text = school.overview_paragraph
                schoolOverview.maxLines = 3

                expandIcon.setOnClickListener {
                    val isExpanded = schoolOverview.maxLines > 3
                    schoolOverview.maxLines = if (isExpanded) 3 else Int.MAX_VALUE
                    expandIcon.setImageResource(if (isExpanded) R.drawable.down else R.drawable.ic_expand_less)
                }
            }
        }
    }
}