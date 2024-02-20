package com.example.learnenglishwordsapp.data.mappers

import androidx.room.Entity
import com.example.learnenglishwordsapp.data.database.WordDbModel
import com.example.learnenglishwordsapp.domain.entity.Word

class WordMapper {

    fun mapEntityToDbModel(entity: Word): WordDbModel {
        return WordDbModel(
            original = entity.original,
            translation = entity.translation,
            correctAnswersCount = entity.correctAnswersCount,
        )
    }

    fun mapListEntityToListDbModel(listEntity: List<Word>): List<WordDbModel> {
        return listEntity.map {
            mapEntityToDbModel(it)
        }
    }

    fun mapDbModelToEntity(dbModel: WordDbModel): Word {
        return Word(
            original = dbModel.original,
            translation = dbModel.translation,
            correctAnswersCount = dbModel.correctAnswersCount,
        )
    }

    fun mapListDbModelToListEntity(listDbModel: List<WordDbModel>): List<Word> {
        return listDbModel.map {
            mapDbModelToEntity(it)
        }
    }
}