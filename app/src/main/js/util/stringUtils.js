const CDN_JOB = 'https://ph-files.imgix.net';
const CDN_AVATAR = 'https://ph-avatars.imgix.net';
String.prototype.formatUnicorn = String.prototype.formatUnicorn || function () {
    "use strict";
    var str = this.toString();
    if (arguments.length) {
        var t = typeof arguments[0];
        var key;
        var args = ("string" === t || "number" === t) ?
            Array.prototype.slice.call(arguments)
            : arguments[0];

        for (key in args) {
            str = str.replace(new RegExp("\\{" + key + "\\}", "gi"), args[key]);
        }
    }

    return str;
};

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