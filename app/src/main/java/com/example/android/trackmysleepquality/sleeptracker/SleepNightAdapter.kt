/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality.sleeptracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.databinding.ListItemSleepNightBinding

class SleepNightAdapter(val  clickListener: SleepNightListener) : ListAdapter<SleepNight,   // ListAdapter is used to introduce the SleepNightAdapter that it uses an list of sleepNight objects to display into recyclerView
        SleepNightAdapter.ViewHolder>(SleepNightDiffCallback()) {



    /*
    We don't need to write things below to letting the SleepNightAdapter to know that its using list of SleepNight object to show it into RecylerView

    var data = listOf<SleepNight>()
    set(value){
        field = value
        notifyDataSetChanged()
    }
*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(getItem(position)!!, clickListener)


       /*
       This is also not needed to write because ListAdapter provide us with an function getItem.


       val item = data[position]

       */


    }



   /*

    This override method is also not needed to write because ListAdapter takes care of the size of list provided.

    override fun getItemCount(): Int {
       return data.size
    }

    */

    class ViewHolder private constructor(val binding: ListItemSleepNightBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: SleepNight, clickListener: SleepNightListener) {

            binding.sleep = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
     /*
         We know don't need to define this variables to update the fragment, we can use dataBinding in XML


         val res = itemView.context.resources
            binding.sleepLength.text =
                convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
            binding.qualityString.text = convertNumericQualityToString(item.sleepQuality, res)
            binding.qualityImage.setImageResource(
                when (item.sleepQuality) {
                    0 -> R.drawable.ic_sleep_0
                    1 -> R.drawable.ic_sleep_1
                    2 -> R.drawable.ic_sleep_2
                    3 -> R.drawable.ic_sleep_3
                    4 -> R.drawable.ic_sleep_4
                    5 -> R.drawable.ic_sleep_5
                    else -> R.drawable.ic_sleep_active
                }
            )


            */
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemSleepNightBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }


   // This class is used on an alternate to notifydatasetchanged for better performance, its updates the list by finding the exact difference using an algorithm.
    class SleepNightDiffCallback: DiffUtil.ItemCallback<SleepNight>(){
        override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
            return oldItem.nightId == newItem.nightId
        }

        override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
            return oldItem == newItem
        }

    }

    class SleepNightListener(val clickListener: (sleepId: Long) -> Unit){
        fun onClick(night: SleepNight) = clickListener(night.nightId)

    }


}
