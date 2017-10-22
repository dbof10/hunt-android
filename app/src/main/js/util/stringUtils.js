const CDN_JOB = 'https://ph-files.imgix.net';
const CDN_AVATAR = 'https://ph-avatars.imgix.net';

export function formatUnit(number) {
    let dividedNumber = number / 1000;
    return dividedNumber + 'K';
}

export function getImageUrl(uuid, size) {
    return `${CDN_JOB}/${uuid}?w=${size}`
}

export function getAvatarUrl(uuid, size) {
    return `${CDN_AVATAR}/${uuid}/original?w=${size}`
}