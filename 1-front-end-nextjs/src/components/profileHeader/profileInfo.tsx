import React from 'react'
import { ProfileHeader } from './profileHeader'
export function ProfileInfo() {
  return (
    <div>
        <ProfileHeader
              username="mkbhd"
              verified
              following
              posts={1861}
              followers="4M"
              followingCount={454}
              name="Marques Brownlee"
              bio="I promise I won't overdo the filters."
              website="https://mkbhd.com"
              avatar="https://cdn-icons-png.flaticon.com/512/9131/9131529.png"
        />
    </div>
  )
}

