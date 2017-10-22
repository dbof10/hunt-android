const QUERY_JOB = 'query JobsPage($cursor: String, $query: String, $locations: [String!], $skills: [String!], $roles: [String!], $remote_ok: Boolean, $upvoted: Boolean, $product_ids: [String!], $promoted: Boolean) ' +
    '{ job_board(query: $query, locations: $locations, skills: $skills, roles: $roles, remote_ok: $remote_ok, upvoted: $upvoted, product_ids: $product_ids, promoted: $promoted) ' +
    '{    jobs_count  jobs(query: $query, locations: $locations, skills: $skills, roles: $roles, remote_ok: $remote_ok, upvoted: $upvoted, product_ids: $product_ids, promoted: $promoted, first: 20, after: $cursor) ' +
    '{      edges {        node {          ...JobBoardItem     }  }' +
    '      pageInfo {        endCursor        hasNextPage  }   }   }}' +
    'fragment UserImageLink on User {  id  _id  username  ...UserImage } ' +
    'fragment UserImage on User {  id  post_upvote_streak } ' +
    'fragment JobBoardItem on Job {  id  company_name  description  image_uuid  job_title  job_type  locations  remote_ok  url  currency_code  salary_max  salary_min  external_created_at ' +
    ' posts {    id    name    slug    tagline    thumbnail { ...MediaThumbnail  }  }  makers {    ...UserImageLink  } }fragment MediaThumbnail on Media {  id  image_uuid}';

const QUERY_MEETUP = 'query MeetupsPage($type: MeetupType!) { meetups(first: 50, type: $type) ' +
    '{ edges {   node { id   description  event_date  event_url  hosts { ...UserImageLink  } title }  }  } } ' +
    'fragment UserImageLink on User {   id   _id   username ...UserImage} ' +
    'fragment UserImage on User { id post_upvote_streak }';

export function buildJobRequest(offset) {

    return {
        query: QUERY_JOB,
        variables: {
            skills: [],
            roles: [],
            locations: [],
            product_ids: [],
            promoted: true,
            cursor: offset
        },
        operationName: "JobsPage"
    }
}

export function buildMeetupRequest(type) {

    return {
        query: QUERY_MEETUP,
        variables: {
            type: type
        },
        operationName: "MeetupsPage"
    }
}