package com.pajokka.githubuser.di

import android.app.Application
import com.pajokka.githubuser.data.UserRepository
import com.pajokka.githubuser.data.source.local.LocalDataSource
import com.pajokka.githubuser.data.source.remote.RemoteDataSource
import com.pajokka.githubuser.ui.setting.SettingsViewModel
import com.pajokka.githubuser.ui.detail.followers.FollowersViewModel
import com.pajokka.githubuser.ui.detail.following.FollowingViewModel
import com.pajokka.githubuser.ui.favorite.FavoriteViewModel
import com.pajokka.githubuser.ui.home.HomeViewModel
import com.pajokka.githubuser.utils.SettingPreference
import com.pajokka.githubuser.utils.ViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class KodeinInjection : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@KodeinInjection))

        bind() from this.provider {
            RemoteDataSource()
        }
        bind() from this.provider {
            applicationContext
        }

        bind<UserRepository>() with this.provider {
            UserRepository(this.instance(), this.instance())
        }

        bind() from this.provider {
            LocalDataSource(this.instance())
        }

        bind() from this.provider {
            FollowersViewModel(this.instance())
        }

        bind() from this.provider {
            FollowingViewModel(this.instance())
        }

        bind() from this.provider {
            SettingsViewModel(this.instance())
        }

        bind() from this.provider {
            HomeViewModel(this.instance(), this.instance())
        }

        bind() from this.provider {
            FavoriteViewModel(this.instance())
        }

        bind() from this.provider {
            SettingPreference(this.instance())
        }

        bind() from this.singleton {
            ViewModelFactory(this.instance(), this.instance())
        }

    }

}