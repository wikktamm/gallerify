package com.example.gallerify.utils.resources

sealed class UserResource {
    class LoggedOut : UserResource()
    class LoggedIn : UserResource()
}