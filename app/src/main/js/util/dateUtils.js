
export const formatCurrentDate = (nowMilliseconds) => {

    const now = new Date(nowMilliseconds);
    let year = now.getFullYear();
    let month = now.getMonth() + 1;
    let date = now.getDate();
    if (date < 10) {
        date = '0' + date;
    }
    if (month < 10) {
        month = '0' + month;
    }
    return `${year}-${month}-${date}`
};


export const formatDate = (isoDate) => {
    const newDate = new Date(isoDate);
    let year = newDate.getFullYear();
    let month = newDate.getMonth() + 1;
    let date = newDate.getDate();
    let hour = newDate.getHours();
    let minute = newDate.getMinutes();

    if (date < 10) {
        date = '0' + date;
    }
    if (month < 10) {
        month = '0' + month;
    }

    if (hour < 10) {
        hour = '0' + hour;
    }

    if (minute < 10) {
        minute = '0' + minute;
    }



    return `${date}/${month}/${year} - ${hour}:${minute}`
};