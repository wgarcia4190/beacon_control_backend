--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.9
-- Dumped by pg_dump version 9.6.5

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner:
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner:
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- Name: utc_timestamp(); Type: FUNCTION; Schema: public; Owner: Wilson
--

CREATE FUNCTION utc_timestamp() RETURNS timestamp without time zone
    LANGUAGE sql
    AS $$
          SELECT NOW() AT time zone 'utc';
        $$;


ALTER FUNCTION public.utc_timestamp() OWNER TO "Wilson";

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: account_extensions; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE account_extensions (
    account_id integer,
    extension_name character varying NOT NULL
);


ALTER TABLE account_extensions OWNER TO "Wilson";

--
-- Name: accounts; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE accounts (
    id integer NOT NULL,
    name character varying NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    brand_id integer NOT NULL
);


ALTER TABLE accounts OWNER TO "Wilson";

--
-- Name: accounts_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE accounts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE accounts_id_seq OWNER TO "Wilson";

--
-- Name: accounts_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE accounts_id_seq OWNED BY accounts.id;


--
-- Name: activities; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE activities (
    id integer NOT NULL,
    name character varying,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    payload text,
    scheme character varying
);


ALTER TABLE activities OWNER TO "Wilson";

--
-- Name: activities_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE activities_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE activities_id_seq OWNER TO "Wilson";

--
-- Name: activities_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE activities_id_seq OWNED BY activities.id;


--
-- Name: admins; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE admins (
    id integer NOT NULL,
    email character varying DEFAULT ''::character varying NOT NULL,
    encrypted_password character varying DEFAULT ''::character varying NOT NULL,
    reset_password_token character varying,
    reset_password_sent_at timestamp without time zone,
    remember_created_at timestamp without time zone,
    sign_in_count integer DEFAULT 0 NOT NULL,
    current_sign_in_at timestamp without time zone,
    last_sign_in_at timestamp without time zone,
    current_sign_in_ip character varying,
    last_sign_in_ip character varying,
    confirmation_token character varying,
    confirmed_at timestamp without time zone,
    confirmation_sent_at timestamp without time zone,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    default_beacon_uuid character varying,
    account_id integer,
    role integer DEFAULT 0,
    correlation_id character varying,
    walkthrough boolean DEFAULT false
);


ALTER TABLE admins OWNER TO "Wilson";

--
-- Name: admins_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE admins_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE admins_id_seq OWNER TO "Wilson";

--
-- Name: admins_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE admins_id_seq OWNED BY admins.id;


--
-- Name: application_extensions; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE application_extensions (
    id integer NOT NULL,
    application_id integer,
    configuration text,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    extension_name character varying NOT NULL
);


ALTER TABLE application_extensions OWNER TO "Wilson";

--
-- Name: application_extensions_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE application_extensions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE application_extensions_id_seq OWNER TO "Wilson";

--
-- Name: application_extensions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE application_extensions_id_seq OWNED BY application_extensions.id;


--
-- Name: application_settings; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE application_settings (
    id integer NOT NULL,
    application_id integer NOT NULL,
    extension_name character varying NOT NULL,
    type character varying NOT NULL,
    key character varying NOT NULL,
    value text,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL
);


ALTER TABLE application_settings OWNER TO "Wilson";

--
-- Name: application_settings_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE application_settings_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE application_settings_id_seq OWNER TO "Wilson";

--
-- Name: application_settings_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE application_settings_id_seq OWNED BY application_settings.id;


--
-- Name: applications; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE applications (
    id integer NOT NULL,
    name character varying,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    account_id integer,
    test boolean DEFAULT false,
    default_app boolean
);


ALTER TABLE applications OWNER TO "Wilson";

--
-- Name: applications_beacons_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE applications_beacons_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE applications_beacons_id_seq OWNER TO "Wilson";

--
-- Name: applications_beacons; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE applications_beacons (
    id integer DEFAULT nextval('applications_beacons_id_seq'::regclass) NOT NULL,
    application_id integer,
    beacon_id integer
);


ALTER TABLE applications_beacons OWNER TO "Wilson";

--
-- Name: applications_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE applications_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE applications_id_seq OWNER TO "Wilson";

--
-- Name: applications_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE applications_id_seq OWNED BY applications.id;


--
-- Name: applications_zones; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE applications_zones (
    id integer NOT NULL,
    application_id integer,
    zone_id integer
);


ALTER TABLE applications_zones OWNER TO "Wilson";

--
-- Name: applications_zones_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE applications_zones_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE applications_zones_id_seq OWNER TO "Wilson";

--
-- Name: applications_zones_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE applications_zones_id_seq OWNED BY applications_zones.id;


--
-- Name: beacon_configs; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE beacon_configs (
    id integer NOT NULL,
    beacon_id integer,
    data text,
    beacon_updated_at timestamp without time zone,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL
);


ALTER TABLE beacon_configs OWNER TO "Wilson";

--
-- Name: beacon_configs_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE beacon_configs_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE beacon_configs_id_seq OWNER TO "Wilson";

--
-- Name: beacon_configs_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE beacon_configs_id_seq OWNED BY beacon_configs.id;


--
-- Name: beacon_presence_stats; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE beacon_presence_stats (
    id integer NOT NULL,
    proximity_id character varying NOT NULL,
    date date NOT NULL,
    hour integer NOT NULL,
    users_count integer DEFAULT 0
);


ALTER TABLE beacon_presence_stats OWNER TO "Wilson";

--
-- Name: beacon_presence_stats_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE beacon_presence_stats_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE beacon_presence_stats_id_seq OWNER TO "Wilson";

--
-- Name: beacon_presence_stats_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE beacon_presence_stats_id_seq OWNED BY beacon_presence_stats.id;


--
-- Name: beacon_proximity_fields; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE beacon_proximity_fields (
    id integer NOT NULL,
    name character varying,
    value character varying,
    beacon_id integer,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE beacon_proximity_fields OWNER TO "Wilson";

--
-- Name: beacon_proximity_fields_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE beacon_proximity_fields_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE beacon_proximity_fields_id_seq OWNER TO "Wilson";

--
-- Name: beacon_proximity_fields_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE beacon_proximity_fields_id_seq OWNED BY beacon_proximity_fields.id;


--
-- Name: beacons; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE beacons (
    id integer NOT NULL,
    name character varying,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    lat numeric(9,6),
    lng numeric(9,6),
    floor integer,
    account_id integer,
    zone_id integer,
    manager_id integer,
    location character varying,
    protocol character varying DEFAULT 'iBeacon'::character varying,
    vendor character varying DEFAULT 'Other'::character varying,
    proximity_uuid character varying
);


ALTER TABLE beacons OWNER TO "Wilson";

--
-- Name: beacons_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE beacons_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE beacons_id_seq OWNER TO "Wilson";

--
-- Name: beacons_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE beacons_id_seq OWNED BY beacons.id;


--
-- Name: brands; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE brands (
    id integer NOT NULL,
    name character varying NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL
);


ALTER TABLE brands OWNER TO "Wilson";

--
-- Name: brands_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE brands_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE brands_id_seq OWNER TO "Wilson";

--
-- Name: brands_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE brands_id_seq OWNED BY brands.id;


--
-- Name: coupon_images; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE coupon_images (
    id integer NOT NULL,
    coupon_id integer,
    file character varying,
    type character varying,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL
);


ALTER TABLE coupon_images OWNER TO "Wilson";

--
-- Name: coupon_images_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE coupon_images_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE coupon_images_id_seq OWNER TO "Wilson";

--
-- Name: coupon_images_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE coupon_images_id_seq OWNED BY coupon_images.id;


--
-- Name: coupons; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE coupons (
    id integer NOT NULL,
    template character varying,
    name character varying,
    title character varying,
    description text,
    activity_id integer,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    identifier_number character varying,
    unique_identifier_number character varying,
    encoding_type character varying,
    button_font_color character varying,
    button_background_color character varying,
    button_label character varying,
    button_link character varying
);


ALTER TABLE coupons OWNER TO "Wilson";

--
-- Name: coupons_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE coupons_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE coupons_id_seq OWNER TO "Wilson";

--
-- Name: coupons_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE coupons_id_seq OWNED BY coupons.id;


--
-- Name: custom_attributes; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE custom_attributes (
    id integer NOT NULL,
    name character varying,
    value character varying,
    activity_id integer,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL
);


ALTER TABLE custom_attributes OWNER TO "Wilson";

--
-- Name: custom_attributes_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE custom_attributes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE custom_attributes_id_seq OWNER TO "Wilson";

--
-- Name: custom_attributes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE custom_attributes_id_seq OWNED BY custom_attributes.id;


--
-- Name: ext_analytics_beacons_dwell_time_aggregations; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE ext_analytics_beacons_dwell_time_aggregations (
    id integer NOT NULL,
    ext_analytics_dwell_time_aggregation_id integer NOT NULL,
    beacon_id integer NOT NULL
);


ALTER TABLE ext_analytics_beacons_dwell_time_aggregations OWNER TO "Wilson";

--
-- Name: ext_analytics_beacons_dwell_time_aggregations_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE ext_analytics_beacons_dwell_time_aggregations_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ext_analytics_beacons_dwell_time_aggregations_id_seq OWNER TO "Wilson";

--
-- Name: ext_analytics_beacons_dwell_time_aggregations_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE ext_analytics_beacons_dwell_time_aggregations_id_seq OWNED BY ext_analytics_beacons_dwell_time_aggregations.id;


--
-- Name: ext_analytics_beacons_events; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE ext_analytics_beacons_events (
    ext_analytics_event_id integer NOT NULL,
    beacon_id integer NOT NULL
);


ALTER TABLE ext_analytics_beacons_events OWNER TO "Wilson";

--
-- Name: ext_analytics_beacons_zones; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE ext_analytics_beacons_zones (
    ext_analytics_event_id integer NOT NULL,
    zone_id integer NOT NULL
);


ALTER TABLE ext_analytics_beacons_zones OWNER TO "Wilson";

--
-- Name: ext_analytics_dwell_time_aggregations; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE ext_analytics_dwell_time_aggregations (
    id integer NOT NULL,
    application_id integer NOT NULL,
    proximity_id character varying NOT NULL,
    user_id character varying,
    date date NOT NULL,
    "timestamp" timestamp without time zone NOT NULL,
    dwell_time integer DEFAULT 0 NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL
);


ALTER TABLE ext_analytics_dwell_time_aggregations OWNER TO "Wilson";

--
-- Name: ext_analytics_dwell_time_aggregations_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE ext_analytics_dwell_time_aggregations_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ext_analytics_dwell_time_aggregations_id_seq OWNER TO "Wilson";

--
-- Name: ext_analytics_dwell_time_aggregations_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE ext_analytics_dwell_time_aggregations_id_seq OWNED BY ext_analytics_dwell_time_aggregations.id;


--
-- Name: ext_analytics_dwell_time_aggregations_zones; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE ext_analytics_dwell_time_aggregations_zones (
    id integer NOT NULL,
    ext_analytics_dwell_time_aggregation_id integer NOT NULL,
    zone_id integer NOT NULL
);


ALTER TABLE ext_analytics_dwell_time_aggregations_zones OWNER TO "Wilson";

--
-- Name: ext_analytics_dwell_time_aggregations_zones_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE ext_analytics_dwell_time_aggregations_zones_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ext_analytics_dwell_time_aggregations_zones_id_seq OWNER TO "Wilson";

--
-- Name: ext_analytics_dwell_time_aggregations_zones_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE ext_analytics_dwell_time_aggregations_zones_id_seq OWNED BY ext_analytics_dwell_time_aggregations_zones.id;


--
-- Name: ext_analytics_events; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE ext_analytics_events (
    id integer NOT NULL,
    application_id integer NOT NULL,
    proximity_id character varying NOT NULL,
    user_id character varying,
    event_type character varying NOT NULL,
    action_id integer,
    "timestamp" timestamp without time zone NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL
);


ALTER TABLE ext_analytics_events OWNER TO "Wilson";

--
-- Name: ext_analytics_events_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE ext_analytics_events_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ext_analytics_events_id_seq OWNER TO "Wilson";

--
-- Name: ext_analytics_events_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE ext_analytics_events_id_seq OWNED BY ext_analytics_events.id;


--
-- Name: ext_kontakt_io_beacon_assignments; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE ext_kontakt_io_beacon_assignments (
    id integer NOT NULL,
    beacon_id integer NOT NULL,
    unique_id character varying NOT NULL
);


ALTER TABLE ext_kontakt_io_beacon_assignments OWNER TO "Wilson";

--
-- Name: ext_kontakt_io_beacon_assignments_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE ext_kontakt_io_beacon_assignments_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ext_kontakt_io_beacon_assignments_id_seq OWNER TO "Wilson";

--
-- Name: ext_kontakt_io_beacon_assignments_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE ext_kontakt_io_beacon_assignments_id_seq OWNED BY ext_kontakt_io_beacon_assignments.id;


--
-- Name: ext_kontakt_io_mapping; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE ext_kontakt_io_mapping (
    id integer NOT NULL,
    target_type character varying,
    target_id integer,
    kontakt_uid character varying
);


ALTER TABLE ext_kontakt_io_mapping OWNER TO "Wilson";

--
-- Name: ext_kontakt_io_mapping_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE ext_kontakt_io_mapping_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ext_kontakt_io_mapping_id_seq OWNER TO "Wilson";

--
-- Name: ext_kontakt_io_mapping_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE ext_kontakt_io_mapping_id_seq OWNED BY ext_kontakt_io_mapping.id;


--
-- Name: ext_neighbours_zone_neighbours; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE ext_neighbours_zone_neighbours (
    id integer NOT NULL,
    source_id integer,
    target_id integer,
    created_at timestamp without time zone,
    updated_at timestamp without time zone
);


ALTER TABLE ext_neighbours_zone_neighbours OWNER TO "Wilson";

--
-- Name: ext_neighbours_zone_neighbours_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE ext_neighbours_zone_neighbours_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ext_neighbours_zone_neighbours_id_seq OWNER TO "Wilson";

--
-- Name: ext_neighbours_zone_neighbours_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE ext_neighbours_zone_neighbours_id_seq OWNED BY ext_neighbours_zone_neighbours.id;


--
-- Name: ext_presence_beacon_presences; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE ext_presence_beacon_presences (
    id integer NOT NULL,
    beacon_id integer,
    client_id character varying,
    "timestamp" timestamp without time zone,
    present boolean DEFAULT false,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL
);


ALTER TABLE ext_presence_beacon_presences OWNER TO "Wilson";

--
-- Name: ext_presence_beacon_presences_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE ext_presence_beacon_presences_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ext_presence_beacon_presences_id_seq OWNER TO "Wilson";

--
-- Name: ext_presence_beacon_presences_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE ext_presence_beacon_presences_id_seq OWNED BY ext_presence_beacon_presences.id;


--
-- Name: ext_presence_zone_presences; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE ext_presence_zone_presences (
    id integer NOT NULL,
    zone_id integer,
    client_id character varying,
    "timestamp" timestamp without time zone,
    present boolean DEFAULT false,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL
);


ALTER TABLE ext_presence_zone_presences OWNER TO "Wilson";

--
-- Name: ext_presence_zone_presences_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE ext_presence_zone_presences_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ext_presence_zone_presences_id_seq OWNER TO "Wilson";

--
-- Name: ext_presence_zone_presences_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE ext_presence_zone_presences_id_seq OWNED BY ext_presence_zone_presences.id;


--
-- Name: extension_settings; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE extension_settings (
    id integer NOT NULL,
    account_id integer NOT NULL,
    extension_name character varying NOT NULL,
    key character varying NOT NULL,
    value character varying
);


ALTER TABLE extension_settings OWNER TO "Wilson";

--
-- Name: extension_settings_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE extension_settings_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE extension_settings_id_seq OWNER TO "Wilson";

--
-- Name: extension_settings_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE extension_settings_id_seq OWNED BY extension_settings.id;


--
-- Name: mobile_devices; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE mobile_devices (
    id integer NOT NULL,
    user_id integer,
    push_token text,
    os integer NOT NULL,
    environment integer DEFAULT 1 NOT NULL,
    active boolean DEFAULT true NOT NULL,
    last_sign_in_at timestamp without time zone,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    correlation_id character varying
);


ALTER TABLE mobile_devices OWNER TO "Wilson";

--
-- Name: mobile_devices_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE mobile_devices_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mobile_devices_id_seq OWNER TO "Wilson";

--
-- Name: mobile_devices_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE mobile_devices_id_seq OWNED BY mobile_devices.id;


--
-- Name: oauth_access_grants; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE oauth_access_grants (
    id integer NOT NULL,
    resource_owner_id integer NOT NULL,
    application_id integer NOT NULL,
    token character varying NOT NULL,
    expires_in integer NOT NULL,
    redirect_uri text NOT NULL,
    created_at timestamp without time zone NOT NULL,
    revoked_at timestamp without time zone,
    scopes character varying
);


ALTER TABLE oauth_access_grants OWNER TO "Wilson";

--
-- Name: oauth_access_grants_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE oauth_access_grants_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE oauth_access_grants_id_seq OWNER TO "Wilson";

--
-- Name: oauth_access_grants_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE oauth_access_grants_id_seq OWNED BY oauth_access_grants.id;


--
-- Name: oauth_access_tokens; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE oauth_access_tokens (
    id integer NOT NULL,
    resource_owner_id integer,
    application_id integer,
    token character varying NOT NULL,
    refresh_token character varying,
    expires_in integer,
    revoked_at timestamp without time zone,
    created_at timestamp without time zone NOT NULL,
    scopes character varying
);


ALTER TABLE oauth_access_tokens OWNER TO "Wilson";

--
-- Name: oauth_access_tokens_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE oauth_access_tokens_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE oauth_access_tokens_id_seq OWNER TO "Wilson";

--
-- Name: oauth_access_tokens_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE oauth_access_tokens_id_seq OWNED BY oauth_access_tokens.id;


--
-- Name: oauth_applications; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE oauth_applications (
    id integer NOT NULL,
    name character varying NOT NULL,
    uid character varying NOT NULL,
    secret character varying NOT NULL,
    redirect_uri text NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    scopes character varying DEFAULT ''::character varying NOT NULL,
    application_id integer
);


ALTER TABLE oauth_applications OWNER TO "Wilson";

--
-- Name: oauth_applications_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE oauth_applications_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE oauth_applications_id_seq OWNER TO "Wilson";

--
-- Name: oauth_applications_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE oauth_applications_id_seq OWNED BY oauth_applications.id;


--
-- Name: old_passwords; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE old_passwords (
    id integer NOT NULL,
    encrypted_password character varying NOT NULL,
    password_salt character varying,
    password_archivable_type character varying NOT NULL,
    password_archivable_id integer NOT NULL,
    created_at timestamp without time zone
);


ALTER TABLE old_passwords OWNER TO "Wilson";

--
-- Name: old_passwords_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE old_passwords_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE old_passwords_id_seq OWNER TO "Wilson";

--
-- Name: old_passwords_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE old_passwords_id_seq OWNED BY old_passwords.id;


--
-- Name: rpush_apps; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE rpush_apps (
    id integer NOT NULL,
    name character varying NOT NULL,
    environment character varying,
    certificate text,
    password character varying,
    connections integer DEFAULT 1 NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    type character varying NOT NULL,
    auth_key character varying,
    client_id character varying,
    client_secret character varying,
    access_token character varying,
    access_token_expiration timestamp without time zone,
    application_id integer
);


ALTER TABLE rpush_apps OWNER TO "Wilson";

--
-- Name: rpush_apps_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE rpush_apps_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE rpush_apps_id_seq OWNER TO "Wilson";

--
-- Name: rpush_apps_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE rpush_apps_id_seq OWNED BY rpush_apps.id;


--
-- Name: rpush_feedback; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE rpush_feedback (
    id integer NOT NULL,
    device_token character varying(64) NOT NULL,
    failed_at timestamp without time zone NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    app_id integer
);


ALTER TABLE rpush_feedback OWNER TO "Wilson";

--
-- Name: rpush_feedback_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE rpush_feedback_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE rpush_feedback_id_seq OWNER TO "Wilson";

--
-- Name: rpush_feedback_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE rpush_feedback_id_seq OWNED BY rpush_feedback.id;


--
-- Name: rpush_notifications; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE rpush_notifications (
    id integer NOT NULL,
    badge integer,
    device_token character varying(64),
    sound character varying DEFAULT 'default'::character varying,
    alert character varying,
    data text,
    expiry integer DEFAULT 86400,
    delivered boolean DEFAULT false NOT NULL,
    delivered_at timestamp without time zone,
    failed boolean DEFAULT false NOT NULL,
    failed_at timestamp without time zone,
    error_code integer,
    error_description text,
    deliver_after timestamp without time zone,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    alert_is_json boolean DEFAULT false,
    type character varying NOT NULL,
    collapse_key character varying,
    delay_while_idle boolean DEFAULT false NOT NULL,
    registration_ids text,
    app_id integer NOT NULL,
    retries integer DEFAULT 0,
    uri character varying,
    fail_after timestamp without time zone,
    processing boolean DEFAULT false NOT NULL,
    priority integer,
    url_args text,
    category character varying
);


ALTER TABLE rpush_notifications OWNER TO "Wilson";

--
-- Name: rpush_notifications_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE rpush_notifications_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE rpush_notifications_id_seq OWNER TO "Wilson";

--
-- Name: rpush_notifications_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE rpush_notifications_id_seq OWNED BY rpush_notifications.id;


--
-- Name: schema_migrations; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE schema_migrations (
    version character varying NOT NULL
);


ALTER TABLE schema_migrations OWNER TO "Wilson";

--
-- Name: triggers; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE triggers (
    id integer NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    event_type character varying DEFAULT 'enter'::character varying,
    application_id integer,
    dwell_time integer,
    type character varying,
    activity_id integer,
    test boolean DEFAULT false
);


ALTER TABLE triggers OWNER TO "Wilson";

--
-- Name: triggers_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE triggers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE triggers_id_seq OWNER TO "Wilson";

--
-- Name: triggers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE triggers_id_seq OWNED BY triggers.id;


--
-- Name: triggers_sources; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE triggers_sources (
    id integer NOT NULL,
    trigger_id integer NOT NULL,
    source_id integer NOT NULL,
    source_type character varying NOT NULL
);


ALTER TABLE triggers_sources OWNER TO "Wilson";

--
-- Name: triggers_sources_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE triggers_sources_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE triggers_sources_id_seq OWNER TO "Wilson";

--
-- Name: triggers_sources_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE triggers_sources_id_seq OWNED BY triggers_sources.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE users (
    id integer NOT NULL,
    client_id character varying NOT NULL,
    application_id integer
);


ALTER TABLE users OWNER TO "Wilson";

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_id_seq OWNER TO "Wilson";

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- Name: zone_presence_stats; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE zone_presence_stats (
    id integer NOT NULL,
    zone_id integer NOT NULL,
    date date NOT NULL,
    hour integer NOT NULL,
    users_count integer DEFAULT 0
);


ALTER TABLE zone_presence_stats OWNER TO "Wilson";

--
-- Name: zone_presence_stats_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE zone_presence_stats_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE zone_presence_stats_id_seq OWNER TO "Wilson";

--
-- Name: zone_presence_stats_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE zone_presence_stats_id_seq OWNED BY zone_presence_stats.id;


--
-- Name: zones; Type: TABLE; Schema: public; Owner: Wilson
--

CREATE TABLE zones (
    id integer NOT NULL,
    name character varying NOT NULL,
    account_id integer NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    description text,
    manager_id integer,
    color character varying,
    beacons_count integer DEFAULT 0
);


ALTER TABLE zones OWNER TO "Wilson";

--
-- Name: zones_id_seq; Type: SEQUENCE; Schema: public; Owner: Wilson
--

CREATE SEQUENCE zones_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE zones_id_seq OWNER TO "Wilson";

--
-- Name: zones_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Wilson
--

ALTER SEQUENCE zones_id_seq OWNED BY zones.id;


--
-- Name: accounts id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY accounts ALTER COLUMN id SET DEFAULT nextval('accounts_id_seq'::regclass);


--
-- Name: activities id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY activities ALTER COLUMN id SET DEFAULT nextval('activities_id_seq'::regclass);


--
-- Name: admins id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY admins ALTER COLUMN id SET DEFAULT nextval('admins_id_seq'::regclass);


--
-- Name: application_extensions id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY application_extensions ALTER COLUMN id SET DEFAULT nextval('application_extensions_id_seq'::regclass);


--
-- Name: application_settings id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY application_settings ALTER COLUMN id SET DEFAULT nextval('application_settings_id_seq'::regclass);


--
-- Name: applications id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY applications ALTER COLUMN id SET DEFAULT nextval('applications_id_seq'::regclass);


--
-- Name: applications_zones id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY applications_zones ALTER COLUMN id SET DEFAULT nextval('applications_zones_id_seq'::regclass);


--
-- Name: beacon_configs id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY beacon_configs ALTER COLUMN id SET DEFAULT nextval('beacon_configs_id_seq'::regclass);


--
-- Name: beacon_presence_stats id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY beacon_presence_stats ALTER COLUMN id SET DEFAULT nextval('beacon_presence_stats_id_seq'::regclass);


--
-- Name: beacon_proximity_fields id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY beacon_proximity_fields ALTER COLUMN id SET DEFAULT nextval('beacon_proximity_fields_id_seq'::regclass);


--
-- Name: beacons id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY beacons ALTER COLUMN id SET DEFAULT nextval('beacons_id_seq'::regclass);


--
-- Name: brands id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY brands ALTER COLUMN id SET DEFAULT nextval('brands_id_seq'::regclass);


--
-- Name: coupon_images id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY coupon_images ALTER COLUMN id SET DEFAULT nextval('coupon_images_id_seq'::regclass);


--
-- Name: coupons id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY coupons ALTER COLUMN id SET DEFAULT nextval('coupons_id_seq'::regclass);


--
-- Name: custom_attributes id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY custom_attributes ALTER COLUMN id SET DEFAULT nextval('custom_attributes_id_seq'::regclass);


--
-- Name: ext_analytics_beacons_dwell_time_aggregations id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY ext_analytics_beacons_dwell_time_aggregations ALTER COLUMN id SET DEFAULT nextval('ext_analytics_beacons_dwell_time_aggregations_id_seq'::regclass);


--
-- Name: ext_analytics_dwell_time_aggregations id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY ext_analytics_dwell_time_aggregations ALTER COLUMN id SET DEFAULT nextval('ext_analytics_dwell_time_aggregations_id_seq'::regclass);


--
-- Name: ext_analytics_dwell_time_aggregations_zones id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY ext_analytics_dwell_time_aggregations_zones ALTER COLUMN id SET DEFAULT nextval('ext_analytics_dwell_time_aggregations_zones_id_seq'::regclass);


--
-- Name: ext_analytics_events id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY ext_analytics_events ALTER COLUMN id SET DEFAULT nextval('ext_analytics_events_id_seq'::regclass);


--
-- Name: ext_kontakt_io_beacon_assignments id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY ext_kontakt_io_beacon_assignments ALTER COLUMN id SET DEFAULT nextval('ext_kontakt_io_beacon_assignments_id_seq'::regclass);


--
-- Name: ext_kontakt_io_mapping id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY ext_kontakt_io_mapping ALTER COLUMN id SET DEFAULT nextval('ext_kontakt_io_mapping_id_seq'::regclass);


--
-- Name: ext_neighbours_zone_neighbours id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY ext_neighbours_zone_neighbours ALTER COLUMN id SET DEFAULT nextval('ext_neighbours_zone_neighbours_id_seq'::regclass);


--
-- Name: ext_presence_beacon_presences id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY ext_presence_beacon_presences ALTER COLUMN id SET DEFAULT nextval('ext_presence_beacon_presences_id_seq'::regclass);


--
-- Name: ext_presence_zone_presences id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY ext_presence_zone_presences ALTER COLUMN id SET DEFAULT nextval('ext_presence_zone_presences_id_seq'::regclass);


--
-- Name: extension_settings id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY extension_settings ALTER COLUMN id SET DEFAULT nextval('extension_settings_id_seq'::regclass);


--
-- Name: mobile_devices id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY mobile_devices ALTER COLUMN id SET DEFAULT nextval('mobile_devices_id_seq'::regclass);


--
-- Name: oauth_access_grants id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY oauth_access_grants ALTER COLUMN id SET DEFAULT nextval('oauth_access_grants_id_seq'::regclass);


--
-- Name: oauth_access_tokens id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY oauth_access_tokens ALTER COLUMN id SET DEFAULT nextval('oauth_access_tokens_id_seq'::regclass);


--
-- Name: oauth_applications id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY oauth_applications ALTER COLUMN id SET DEFAULT nextval('oauth_applications_id_seq'::regclass);


--
-- Name: old_passwords id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY old_passwords ALTER COLUMN id SET DEFAULT nextval('old_passwords_id_seq'::regclass);


--
-- Name: rpush_apps id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY rpush_apps ALTER COLUMN id SET DEFAULT nextval('rpush_apps_id_seq'::regclass);


--
-- Name: rpush_feedback id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY rpush_feedback ALTER COLUMN id SET DEFAULT nextval('rpush_feedback_id_seq'::regclass);


--
-- Name: rpush_notifications id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY rpush_notifications ALTER COLUMN id SET DEFAULT nextval('rpush_notifications_id_seq'::regclass);


--
-- Name: triggers id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY triggers ALTER COLUMN id SET DEFAULT nextval('triggers_id_seq'::regclass);


--
-- Name: triggers_sources id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY triggers_sources ALTER COLUMN id SET DEFAULT nextval('triggers_sources_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- Name: zone_presence_stats id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY zone_presence_stats ALTER COLUMN id SET DEFAULT nextval('zone_presence_stats_id_seq'::regclass);


--
-- Name: zones id; Type: DEFAULT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY zones ALTER COLUMN id SET DEFAULT nextval('zones_id_seq'::regclass);


--
-- Data for Name: account_extensions; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY account_extensions (account_id, extension_name) FROM stdin;
1	Presence
\.


--
-- Data for Name: accounts; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY accounts (id, name, created_at, updated_at, brand_id) FROM stdin;
1	admin@example.com	2017-04-17 21:38:16.38497	2017-04-17 21:38:16.38497	1
\.


--
-- Name: accounts_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('accounts_id_seq', 1, true);


--
-- Data for Name: activities; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY activities (id, name, created_at, updated_at, payload, scheme) FROM stdin;
17	Hello world!	2017-09-27 14:00:57.256	2017-09-27 14:00:57.256	{}	custom
20	Beacon 2 Nearby Activity	2017-10-01 10:18:19.451	2017-10-01 10:18:19.451	\N	custom
22	Url Activity	2017-10-01 16:46:46.264	2017-10-01 16:46:46.264	www.google.com	url
23	URL Activity	2017-10-01 16:49:37.072	2017-10-01 16:49:37.072	www.google.com	url
30	Coupon Activity 5	2017-10-04 20:25:52.132	2017-10-04 20:25:52.132	\N	coupon
\.


--
-- Name: activities_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('activities_id_seq', 30, true);


--
-- Data for Name: admins; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY admins (id, email, encrypted_password, reset_password_token, reset_password_sent_at, remember_created_at, sign_in_count, current_sign_in_at, last_sign_in_at, current_sign_in_ip, last_sign_in_ip, confirmation_token, confirmed_at, confirmation_sent_at, created_at, updated_at, default_beacon_uuid, account_id, role, correlation_id, walkthrough) FROM stdin;
1	admin@example.com	$2a$10$y56ZxepReb8DXkYxdl0XTO7EZOU7G0kozplTKv6r91iTlz32SvaUq	\N	\N	\N	0	\N	\N	\N	\N	bb527a7dcf1d15cf8d17e3e0181d322d4177988a0fc8398bc6e0b783460f4c57	2017-04-17 21:38:18.365567	2017-04-17 21:38:16.68618	2017-04-17 21:38:16.389192	2017-04-17 21:38:16.389192	\N	1	0	\N	f
15	garcia.m.wilson@gmail.com	glADFfQUClQ3D9qp9bKABKLiIsCSI3P0W3AIBA3XCONBEwGtDPn4FQ==	\N	\N	\N	0	\N	\N	\N	\N	3016496539166067961	2017-05-02 20:31:30.593	\N	2017-05-02 20:31:30.594	2017-05-06 14:00:59.308	df423e23-5423-2345-2345-234523452345	1	0	\N	f
21	malabro37@gmail.com	nrUV5IfzDHGAPUQ5pnjN/YrD6himxaKxnSmZiTLWz4aDzfCWqNYQSg==	\N	\N	\N	1161	2017-10-29 09:18:12.174	2017-10-22 16:38:49.948	10.0.0.22	10.0.0.22	5990672920563371169	2017-09-16 16:53:02.973	2017-09-16 16:34:00.898	2017-09-16 16:34:00.898	2017-10-29 09:18:12.175	\N	1	0	\N	f
\.


--
-- Name: admins_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('admins_id_seq', 21, true);


--
-- Data for Name: application_extensions; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY application_extensions (id, application_id, configuration, created_at, updated_at, extension_name) FROM stdin;
1	1	\N	2017-04-17 21:38:18.456351	2017-04-17 21:38:18.456351	Presence
\.


--
-- Name: application_extensions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('application_extensions_id_seq', 1, true);


--
-- Data for Name: application_settings; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY application_settings (id, application_id, extension_name, type, key, value, created_at, updated_at) FROM stdin;
\.


--
-- Name: application_settings_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('application_settings_id_seq', 1, false);


--
-- Data for Name: applications; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY applications (id, name, created_at, updated_at, account_id, test, default_app) FROM stdin;
1	App	2017-04-17 21:38:18.413615	2017-04-17 21:38:18.413615	1	t	f
17	AppTestWithCoupons	2017-10-02 20:22:52.499	2017-10-02 20:22:52.499	1	t	t
\.


--
-- Data for Name: applications_beacons; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY applications_beacons (id, application_id, beacon_id) FROM stdin;
15	17	34
16	17	28
17	17	28
18	17	35
19	17	27
\.


--
-- Name: applications_beacons_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('applications_beacons_id_seq', 19, true);


--
-- Name: applications_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('applications_id_seq', 17, true);


--
-- Data for Name: applications_zones; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY applications_zones (id, application_id, zone_id) FROM stdin;
\.


--
-- Name: applications_zones_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('applications_zones_id_seq', 1, false);


--
-- Data for Name: beacon_configs; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY beacon_configs (id, beacon_id, data, beacon_updated_at, created_at, updated_at) FROM stdin;
21	27	{"device_id":"Unknown","vendor":"BlueSense","last_action":"2017-09-23T19:42:52.368Z","battery":"Unknown","firmware":"Unknown","average_connection_intervals":"Unknown","status":"Active"}	2017-09-23 15:42:52.428	2017-09-23 15:42:52.436	2017-09-23 15:42:52.436
22	28	{"device_id":"Unknown","vendor":"Glimworm","last_action":"2017-09-24T13:48:12.640Z","battery":"Unknown","firmware":"Unknown","average_connection_intervals":"Unknown","status":"Active"}	2017-09-24 09:48:12.703	2017-09-24 09:48:12.713	2017-09-24 09:48:12.713
28	34	{"device_id":"Unknown","vendor":"Estimote","last_action":"2017-09-29T13:58:23.562Z","battery":"Unknown","firmware":"Unknown","average_connection_intervals":"Unknown","status":"Active"}	2017-09-29 09:58:23.618	2017-09-29 09:58:23.624	2017-09-29 09:58:23.624
29	35	{"device_id":"Unknown","vendor":"Kontakt","last_action":"2017-09-29T15:51:43.070Z","battery":"Unknown","firmware":"Unknown","average_connection_intervals":"Unknown","status":"Active"}	2017-09-29 11:51:43.088	2017-09-29 11:51:43.093	2017-09-29 11:51:43.093
\.


--
-- Name: beacon_configs_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('beacon_configs_id_seq', 30, true);


--
-- Data for Name: beacon_presence_stats; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY beacon_presence_stats (id, proximity_id, date, hour, users_count) FROM stdin;
\.


--
-- Name: beacon_presence_stats_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('beacon_presence_stats_id_seq', 1, false);


--
-- Data for Name: beacon_proximity_fields; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY beacon_proximity_fields (id, name, value, beacon_id, created_at, updated_at) FROM stdin;
78	minor	1	28	2017-09-24 09:48:12.709293	2017-09-24 09:48:12.709293
79	major	10	28	2017-09-24 09:48:12.709293	2017-09-24 09:48:12.709293
90	minor	1	34	2017-09-29 09:58:23.622036	2017-09-29 09:58:23.622036
91	major	10	34	2017-09-29 09:58:23.622036	2017-09-29 09:58:23.622036
92	namespace	qwerqwer	35	2017-09-29 11:51:43.092364	2017-09-29 11:51:43.092364
93	instance	qwerqwer	35	2017-09-29 11:51:43.092364	2017-09-29 11:51:43.092364
94	interval	3241234	35	2017-09-29 11:51:43.092364	2017-09-29 11:51:43.092364
77	major	001e	27	2017-09-23 15:42:52.435415	2017-09-23 15:42:52.435415
76	minor	0001	27	2017-09-23 15:42:52.435415	2017-09-23 15:42:52.435415
\.


--
-- Name: beacon_proximity_fields_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('beacon_proximity_fields_id_seq', 96, true);


--
-- Data for Name: beacons; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY beacons (id, name, created_at, updated_at, lat, lng, floor, account_id, zone_id, manager_id, location, protocol, vendor, proximity_uuid) FROM stdin;
34	Beacon Test with Activity	2017-09-29 09:58:23.618	2017-09-29 09:58:23.618	18.462400	-69.988200	0	1	\N	21	Calle María Trinidad Sánchez, Santo Domingo, Dominican Republic	iBeacon	Estimote	12341234-1235-2363-4746-dfad1235adfa
35	Beacon test with activity 2	2017-09-29 11:51:43.088	2017-09-29 11:51:43.088	18.462400	-69.988200	0	1	\N	21	Calle María Trinidad Sánchez, Santo Domingo, Dominican Republic	Eddystone	Kontakt	\N
27	Beacon Zone 2	2017-09-23 15:42:52.428	2017-09-23 15:42:52.428	18.466800	-69.894400	1	1	5	21	Calle Arzobispo Portes, Santo Domingo 10208, Dominican Republic	iBeacon	BlueSense	7890afcb-c789-0abd-c90d-fac98d2389aa
28	Beacon No Zone	2017-09-24 09:48:12.703	2017-09-25 15:28:09.919	18.466700	-69.894400	0	1	\N	21	Calle Arzobispo Portes, Santo Domingo 10208, Dominican Republic	iBeacon	Glimworm	2345345d-adfa-e435-2346-546dfade1351
\.


--
-- Name: beacons_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('beacons_id_seq', 36, true);


--
-- Data for Name: brands; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY brands (id, name, created_at, updated_at) FROM stdin;
1	Beacon OS	2017-04-17 21:38:16.169004	2017-04-17 21:38:16.169004
\.


--
-- Name: brands_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('brands_id_seq', 1, true);


--
-- Data for Name: coupon_images; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY coupon_images (id, coupon_id, file, type, created_at, updated_at) FROM stdin;
\.


--
-- Name: coupon_images_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('coupon_images_id_seq', 1, false);


--
-- Data for Name: coupons; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY coupons (id, template, name, title, description, activity_id, created_at, updated_at, identifier_number, unique_identifier_number, encoding_type, button_font_color, button_background_color, button_label, button_link) FROM stdin;
1	template_3	Coupon1	Title	Description	30	2017-10-04 20:25:52.157	2017-10-04 20:25:52.157	456	123	code_128	#000000	#000000	\N	\N
\.


--
-- Name: coupons_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('coupons_id_seq', 1, true);


--
-- Data for Name: custom_attributes; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY custom_attributes (id, name, value, activity_id, created_at, updated_at) FROM stdin;
15	text	Your beacon is working.	17	2017-09-27 14:00:57.26	2017-09-27 14:00:57.26
16	text	Title	17	2017-10-29 11:16:16.177886	2017-10-29 11:16:16.177886
17	text	Your beacon is working	30	2017-10-29 11:49:13.636137	2017-10-29 11:49:13.636137
18	text	title	30	2017-10-29 11:50:39.654835	2017-10-29 11:50:39.654835
\.


--
-- Name: custom_attributes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('custom_attributes_id_seq', 26, true);


--
-- Data for Name: ext_analytics_beacons_dwell_time_aggregations; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY ext_analytics_beacons_dwell_time_aggregations (id, ext_analytics_dwell_time_aggregation_id, beacon_id) FROM stdin;
\.


--
-- Name: ext_analytics_beacons_dwell_time_aggregations_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('ext_analytics_beacons_dwell_time_aggregations_id_seq', 1, false);


--
-- Data for Name: ext_analytics_beacons_events; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY ext_analytics_beacons_events (ext_analytics_event_id, beacon_id) FROM stdin;
\.


--
-- Data for Name: ext_analytics_beacons_zones; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY ext_analytics_beacons_zones (ext_analytics_event_id, zone_id) FROM stdin;
\.


--
-- Data for Name: ext_analytics_dwell_time_aggregations; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY ext_analytics_dwell_time_aggregations (id, application_id, proximity_id, user_id, date, "timestamp", dwell_time, created_at, updated_at) FROM stdin;
\.


--
-- Name: ext_analytics_dwell_time_aggregations_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('ext_analytics_dwell_time_aggregations_id_seq', 1, false);


--
-- Data for Name: ext_analytics_dwell_time_aggregations_zones; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY ext_analytics_dwell_time_aggregations_zones (id, ext_analytics_dwell_time_aggregation_id, zone_id) FROM stdin;
\.


--
-- Name: ext_analytics_dwell_time_aggregations_zones_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('ext_analytics_dwell_time_aggregations_zones_id_seq', 1, false);


--
-- Data for Name: ext_analytics_events; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY ext_analytics_events (id, application_id, proximity_id, user_id, event_type, action_id, "timestamp", created_at, updated_at) FROM stdin;
\.


--
-- Name: ext_analytics_events_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('ext_analytics_events_id_seq', 1, false);


--
-- Data for Name: ext_kontakt_io_beacon_assignments; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY ext_kontakt_io_beacon_assignments (id, beacon_id, unique_id) FROM stdin;
\.


--
-- Name: ext_kontakt_io_beacon_assignments_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('ext_kontakt_io_beacon_assignments_id_seq', 1, false);


--
-- Data for Name: ext_kontakt_io_mapping; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY ext_kontakt_io_mapping (id, target_type, target_id, kontakt_uid) FROM stdin;
\.


--
-- Name: ext_kontakt_io_mapping_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('ext_kontakt_io_mapping_id_seq', 1, false);


--
-- Data for Name: ext_neighbours_zone_neighbours; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY ext_neighbours_zone_neighbours (id, source_id, target_id, created_at, updated_at) FROM stdin;
\.


--
-- Name: ext_neighbours_zone_neighbours_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('ext_neighbours_zone_neighbours_id_seq', 1, false);


--
-- Data for Name: ext_presence_beacon_presences; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY ext_presence_beacon_presences (id, beacon_id, client_id, "timestamp", present, created_at, updated_at) FROM stdin;
\.


--
-- Name: ext_presence_beacon_presences_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('ext_presence_beacon_presences_id_seq', 1, false);


--
-- Data for Name: ext_presence_zone_presences; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY ext_presence_zone_presences (id, zone_id, client_id, "timestamp", present, created_at, updated_at) FROM stdin;
\.


--
-- Name: ext_presence_zone_presences_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('ext_presence_zone_presences_id_seq', 1, false);


--
-- Data for Name: extension_settings; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY extension_settings (id, account_id, extension_name, key, value) FROM stdin;
\.


--
-- Name: extension_settings_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('extension_settings_id_seq', 1, false);


--
-- Data for Name: mobile_devices; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY mobile_devices (id, user_id, push_token, os, environment, active, last_sign_in_at, created_at, updated_at, correlation_id) FROM stdin;
1	1		0	0	t	2017-10-29 09:17:56.598	2017-10-29 09:17:56.599	2017-10-29 09:17:56.599	\N
\.


--
-- Name: mobile_devices_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('mobile_devices_id_seq', 1, true);


--
-- Data for Name: oauth_access_grants; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY oauth_access_grants (id, resource_owner_id, application_id, token, expires_in, redirect_uri, created_at, revoked_at, scopes) FROM stdin;
\.


--
-- Name: oauth_access_grants_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('oauth_access_grants_id_seq', 1, false);


--
-- Data for Name: oauth_access_tokens; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY oauth_access_tokens (id, resource_owner_id, application_id, token, refresh_token, expires_in, revoked_at, created_at, scopes) FROM stdin;
\.


--
-- Name: oauth_access_tokens_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('oauth_access_tokens_id_seq', 1, false);


--
-- Data for Name: oauth_applications; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY oauth_applications (id, name, uid, secret, redirect_uri, created_at, updated_at, scopes, application_id) FROM stdin;
5	AppTestWithCoupons	epy9dqozuo80mbzn7p81ei5qqtehfttpgowf7qc5y4n3oln6j0v16ut29ki03a5r	ciwbxtz73ul8g6m0islkd278iqxeloaghrgnzflcu5j78bvqmaud5fb18ba4s9q0	 	2017-10-02 20:22:52.502	2017-10-02 20:22:52.502		17
\.


--
-- Name: oauth_applications_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('oauth_applications_id_seq', 5, true);


--
-- Data for Name: old_passwords; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY old_passwords (id, encrypted_password, password_salt, password_archivable_type, password_archivable_id, created_at) FROM stdin;
1		\N	Admin	1	2017-04-17 21:38:18.37013
\.


--
-- Name: old_passwords_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('old_passwords_id_seq', 1, true);


--
-- Data for Name: rpush_apps; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY rpush_apps (id, name, environment, certificate, password, connections, created_at, updated_at, type, auth_key, client_id, client_secret, access_token, access_token_expiration, application_id) FROM stdin;
\.


--
-- Name: rpush_apps_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('rpush_apps_id_seq', 1, false);


--
-- Data for Name: rpush_feedback; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY rpush_feedback (id, device_token, failed_at, created_at, updated_at, app_id) FROM stdin;
\.


--
-- Name: rpush_feedback_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('rpush_feedback_id_seq', 1, false);


--
-- Data for Name: rpush_notifications; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY rpush_notifications (id, badge, device_token, sound, alert, data, expiry, delivered, delivered_at, failed, failed_at, error_code, error_description, deliver_after, created_at, updated_at, alert_is_json, type, collapse_key, delay_while_idle, registration_ids, app_id, retries, uri, fail_after, processing, priority, url_args, category) FROM stdin;
\.


--
-- Name: rpush_notifications_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('rpush_notifications_id_seq', 1, false);


--
-- Data for Name: schema_migrations; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY schema_migrations (version) FROM stdin;
20140318150120
20140319102517
20140319144148
20140319171243
20140320141825
20140321125154
20140324140107
20140324140315
20140326132500
20140331135016
20140412143422
20140415113816
20140508090613
20140508093529
20140509142114
20140711120841
20140711123617
20140717125019
20140717125907
20140721125813
20140724144830
20140725102814
20140729112713
20140730122442
20140730125938
20140730145817
20140801082534
20140801083012
20140804082822
20140804161437
20140805124442
20140806151652
20140806153251
20140806163044
20140826145615
20140910085815
20140910085816
20140916143308
20140925105330
20141002122854
20141006120634
20141006144639
20141006145426
20141010080746
20141013122934
20141013123157
20141013123226
20141020113558
20141029085601
20141029090340
20141106153724
20141107093146
20150114120017
20150122092552
20150124094727
20150124110000
20150124120000
20150124125549
20150124130000
20150124141054
20150126122743
20150303101219
20150325114219
20150326120850
20150413092622
20150416112405
20150416112406
20150421112348
20150421112721
20150428080849
20150508142632
20150515091857
20150515095220
20150518083738
20150518085027
20150519092520
20150521132628
20150526130552
20150529084304
20150601144754
20150702113036
20150706111028
20150715123712
20150723103804
20150826090401
20150903081952
20150903101425
20150928060329
20150929132753
20151116134144
20151202091632
20160127133722
\.


--
-- Data for Name: triggers; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY triggers (id, created_at, updated_at, event_type, application_id, dwell_time, type, activity_id, test) FROM stdin;
23	2017-10-04 20:25:52.137	2017-10-04 20:25:52.137	leave	17	\N	BeaconTrigger	30	f
\.


--
-- Name: triggers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('triggers_id_seq', 23, true);


--
-- Data for Name: triggers_sources; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY triggers_sources (id, trigger_id, source_id, source_type) FROM stdin;
22	23	27	Beacon
\.


--
-- Name: triggers_sources_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('triggers_sources_id_seq', 22, true);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY users (id, client_id, application_id) FROM stdin;
1	epy9dqozuo80mbzn7p81ei5qqtehfttpgowf7qc5y4n3oln6j0v16ut29ki03a5r	17
\.


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('users_id_seq', 1, false);


--
-- Data for Name: zone_presence_stats; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY zone_presence_stats (id, zone_id, date, hour, users_count) FROM stdin;
\.


--
-- Name: zone_presence_stats_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('zone_presence_stats_id_seq', 1, false);


--
-- Data for Name: zones; Type: TABLE DATA; Schema: public; Owner: Wilson
--

COPY zones (id, name, account_id, created_at, updated_at, description, manager_id, color, beacons_count) FROM stdin;
1	test zone 1	1	2017-04-17 21:38:18.709526	2017-04-17 21:38:18.709526	\N	\N	FF6F3A	\N
2	test zone 2	1	2017-04-17 21:38:18.718388	2017-04-17 21:38:18.718388	\N	\N	FFA800	\N
3	test zone 2	1	2017-04-17 21:38:18.726284	2017-04-17 21:38:18.726284	\N	\N	FFCB00	\N
5	Test Zone 2	1	2017-09-16 22:03:01.13	2017-09-16 22:03:01.13	Testing Service from UI	21	rgb(91, 105, 195)	0
14	Test Zone 3	1	2017-09-24 08:54:52.673	2017-09-24 08:54:52.673	Test Zone	21	rgb(5, 121, 111)	0
17	Zone from Mobile	1	2017-10-22 15:43:05.779	2017-10-22 15:43:05.779	\N	21	rgb(172, 65, 190)	0
19	Zone mobile2	1	2017-10-22 16:28:36.429	2017-10-22 16:28:36.429	\N	21	rgb(155, 205, 95)	0
\.


--
-- Name: zones_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Wilson
--

SELECT pg_catalog.setval('zones_id_seq', 19, true);


--
-- Name: accounts accounts_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY accounts
    ADD CONSTRAINT accounts_pkey PRIMARY KEY (id);


--
-- Name: activities activities_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY activities
    ADD CONSTRAINT activities_pkey PRIMARY KEY (id);


--
-- Name: admins admins_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY admins
    ADD CONSTRAINT admins_pkey PRIMARY KEY (id);


--
-- Name: application_extensions application_extensions_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY application_extensions
    ADD CONSTRAINT application_extensions_pkey PRIMARY KEY (id);


--
-- Name: application_settings application_settings_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY application_settings
    ADD CONSTRAINT application_settings_pkey PRIMARY KEY (id);


--
-- Name: applications_beacons applications_beacons_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY applications_beacons
    ADD CONSTRAINT applications_beacons_pkey PRIMARY KEY (id);


--
-- Name: applications applications_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY applications
    ADD CONSTRAINT applications_pkey PRIMARY KEY (id);


--
-- Name: applications_zones applications_zones_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY applications_zones
    ADD CONSTRAINT applications_zones_pkey PRIMARY KEY (id);


--
-- Name: beacon_configs beacon_configs_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY beacon_configs
    ADD CONSTRAINT beacon_configs_pkey PRIMARY KEY (id);


--
-- Name: beacon_presence_stats beacon_presence_stats_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY beacon_presence_stats
    ADD CONSTRAINT beacon_presence_stats_pkey PRIMARY KEY (id);


--
-- Name: beacon_proximity_fields beacon_proximity_fields_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY beacon_proximity_fields
    ADD CONSTRAINT beacon_proximity_fields_pkey PRIMARY KEY (id);


--
-- Name: beacons beacons_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY beacons
    ADD CONSTRAINT beacons_pkey PRIMARY KEY (id);


--
-- Name: brands brands_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY brands
    ADD CONSTRAINT brands_pkey PRIMARY KEY (id);


--
-- Name: coupon_images coupon_images_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY coupon_images
    ADD CONSTRAINT coupon_images_pkey PRIMARY KEY (id);


--
-- Name: coupons coupons_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY coupons
    ADD CONSTRAINT coupons_pkey PRIMARY KEY (id);


--
-- Name: custom_attributes custom_attributes_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY custom_attributes
    ADD CONSTRAINT custom_attributes_pkey PRIMARY KEY (id);


--
-- Name: ext_analytics_beacons_dwell_time_aggregations ext_analytics_beacons_dwell_time_aggregations_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY ext_analytics_beacons_dwell_time_aggregations
    ADD CONSTRAINT ext_analytics_beacons_dwell_time_aggregations_pkey PRIMARY KEY (id);


--
-- Name: ext_analytics_dwell_time_aggregations ext_analytics_dwell_time_aggregations_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY ext_analytics_dwell_time_aggregations
    ADD CONSTRAINT ext_analytics_dwell_time_aggregations_pkey PRIMARY KEY (id);


--
-- Name: ext_analytics_dwell_time_aggregations_zones ext_analytics_dwell_time_aggregations_zones_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY ext_analytics_dwell_time_aggregations_zones
    ADD CONSTRAINT ext_analytics_dwell_time_aggregations_zones_pkey PRIMARY KEY (id);


--
-- Name: ext_analytics_events ext_analytics_events_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY ext_analytics_events
    ADD CONSTRAINT ext_analytics_events_pkey PRIMARY KEY (id);


--
-- Name: ext_kontakt_io_beacon_assignments ext_kontakt_io_beacon_assignments_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY ext_kontakt_io_beacon_assignments
    ADD CONSTRAINT ext_kontakt_io_beacon_assignments_pkey PRIMARY KEY (id);


--
-- Name: ext_kontakt_io_mapping ext_kontakt_io_mapping_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY ext_kontakt_io_mapping
    ADD CONSTRAINT ext_kontakt_io_mapping_pkey PRIMARY KEY (id);


--
-- Name: ext_neighbours_zone_neighbours ext_neighbours_zone_neighbours_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY ext_neighbours_zone_neighbours
    ADD CONSTRAINT ext_neighbours_zone_neighbours_pkey PRIMARY KEY (id);


--
-- Name: ext_presence_beacon_presences ext_presence_beacon_presences_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY ext_presence_beacon_presences
    ADD CONSTRAINT ext_presence_beacon_presences_pkey PRIMARY KEY (id);


--
-- Name: ext_presence_zone_presences ext_presence_zone_presences_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY ext_presence_zone_presences
    ADD CONSTRAINT ext_presence_zone_presences_pkey PRIMARY KEY (id);


--
-- Name: extension_settings extension_settings_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY extension_settings
    ADD CONSTRAINT extension_settings_pkey PRIMARY KEY (id);


--
-- Name: mobile_devices mobile_devices_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY mobile_devices
    ADD CONSTRAINT mobile_devices_pkey PRIMARY KEY (id);


--
-- Name: oauth_access_grants oauth_access_grants_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY oauth_access_grants
    ADD CONSTRAINT oauth_access_grants_pkey PRIMARY KEY (id);


--
-- Name: oauth_access_tokens oauth_access_tokens_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY oauth_access_tokens
    ADD CONSTRAINT oauth_access_tokens_pkey PRIMARY KEY (id);


--
-- Name: oauth_applications oauth_applications_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY oauth_applications
    ADD CONSTRAINT oauth_applications_pkey PRIMARY KEY (id);


--
-- Name: old_passwords old_passwords_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY old_passwords
    ADD CONSTRAINT old_passwords_pkey PRIMARY KEY (id);


--
-- Name: rpush_apps rpush_apps_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY rpush_apps
    ADD CONSTRAINT rpush_apps_pkey PRIMARY KEY (id);


--
-- Name: rpush_feedback rpush_feedback_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY rpush_feedback
    ADD CONSTRAINT rpush_feedback_pkey PRIMARY KEY (id);


--
-- Name: rpush_notifications rpush_notifications_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY rpush_notifications
    ADD CONSTRAINT rpush_notifications_pkey PRIMARY KEY (id);


--
-- Name: triggers triggers_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY triggers
    ADD CONSTRAINT triggers_pkey PRIMARY KEY (id);


--
-- Name: triggers_sources triggers_sources_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY triggers_sources
    ADD CONSTRAINT triggers_sources_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: zone_presence_stats zone_presence_stats_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY zone_presence_stats
    ADD CONSTRAINT zone_presence_stats_pkey PRIMARY KEY (id);


--
-- Name: zones zones_pkey; Type: CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY zones
    ADD CONSTRAINT zones_pkey PRIMARY KEY (id);


--
-- Name: applications_beacons_index; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX applications_beacons_index ON applications_beacons USING btree (application_id, beacon_id);


--
-- Name: applications_zones_index; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX applications_zones_index ON applications_zones USING btree (application_id, zone_id);


--
-- Name: ext_analytics_beacons_events_idx; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX ext_analytics_beacons_events_idx ON ext_analytics_beacons_events USING btree (ext_analytics_event_id, beacon_id);


--
-- Name: ext_analytics_beacons_zones_idx; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX ext_analytics_beacons_zones_idx ON ext_analytics_beacons_zones USING btree (ext_analytics_event_id, zone_id);


--
-- Name: ext_analytics_dta_beacon_idx; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX ext_analytics_dta_beacon_idx ON ext_analytics_beacons_dwell_time_aggregations USING btree (ext_analytics_dwell_time_aggregation_id, beacon_id);


--
-- Name: ext_analytics_dta_zone_idx; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX ext_analytics_dta_zone_idx ON ext_analytics_dwell_time_aggregations_zones USING btree (ext_analytics_dwell_time_aggregation_id, zone_id);


--
-- Name: index_account_extensions_on_account_id_and_extension_name; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE UNIQUE INDEX index_account_extensions_on_account_id_and_extension_name ON account_extensions USING btree (account_id, extension_name);


--
-- Name: index_admins_on_account_id; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX index_admins_on_account_id ON admins USING btree (account_id);


--
-- Name: index_admins_on_confirmation_token; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE UNIQUE INDEX index_admins_on_confirmation_token ON admins USING btree (confirmation_token);


--
-- Name: index_admins_on_email; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE UNIQUE INDEX index_admins_on_email ON admins USING btree (email);


--
-- Name: index_admins_on_reset_password_token; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE UNIQUE INDEX index_admins_on_reset_password_token ON admins USING btree (reset_password_token);


--
-- Name: index_application_extensions_on_application_id; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX index_application_extensions_on_application_id ON application_extensions USING btree (application_id);


--
-- Name: index_application_settings_on_application_id; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX index_application_settings_on_application_id ON application_settings USING btree (application_id);


--
-- Name: index_applications_extensions; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE UNIQUE INDEX index_applications_extensions ON application_extensions USING btree (application_id, extension_name);


--
-- Name: index_applications_on_account_id; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX index_applications_on_account_id ON applications USING btree (account_id);


--
-- Name: index_beacon_configs_on_beacon_id; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX index_beacon_configs_on_beacon_id ON beacon_configs USING btree (beacon_id);


--
-- Name: index_beacon_proximity_fields_on_beacon_id; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX index_beacon_proximity_fields_on_beacon_id ON beacon_proximity_fields USING btree (beacon_id);


--
-- Name: index_beacons_on_proximity_uuid_and_account_id; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE UNIQUE INDEX index_beacons_on_proximity_uuid_and_account_id ON beacons USING btree (proximity_uuid, account_id);


--
-- Name: index_coupon_images_on_coupon_id; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX index_coupon_images_on_coupon_id ON coupon_images USING btree (coupon_id);


--
-- Name: index_coupons_on_activity_id; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX index_coupons_on_activity_id ON coupons USING btree (activity_id);


--
-- Name: index_coupons_on_template; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX index_coupons_on_template ON coupons USING btree (template);


--
-- Name: index_custom_attributes_on_activity_id; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX index_custom_attributes_on_activity_id ON custom_attributes USING btree (activity_id);


--
-- Name: index_ext_analytics_dwell_time_aggregations_on_application_id; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX index_ext_analytics_dwell_time_aggregations_on_application_id ON ext_analytics_dwell_time_aggregations USING btree (application_id);


--
-- Name: index_ext_analytics_dwell_time_aggregations_on_date; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX index_ext_analytics_dwell_time_aggregations_on_date ON ext_analytics_dwell_time_aggregations USING btree (date);


--
-- Name: index_ext_analytics_dwell_time_aggregations_on_proximity_id; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX index_ext_analytics_dwell_time_aggregations_on_proximity_id ON ext_analytics_dwell_time_aggregations USING btree (proximity_id);


--
-- Name: index_ext_analytics_dwell_time_aggregations_on_timestamp; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX index_ext_analytics_dwell_time_aggregations_on_timestamp ON ext_analytics_dwell_time_aggregations USING btree ("timestamp");


--
-- Name: index_ext_analytics_dwell_time_aggregations_on_user_id; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX index_ext_analytics_dwell_time_aggregations_on_user_id ON ext_analytics_dwell_time_aggregations USING btree (user_id);


--
-- Name: index_ext_analytics_events_on_application_id; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX index_ext_analytics_events_on_application_id ON ext_analytics_events USING btree (application_id);


--
-- Name: index_ext_analytics_events_on_event_type; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX index_ext_analytics_events_on_event_type ON ext_analytics_events USING btree (event_type);


--
-- Name: index_ext_analytics_events_on_proximity_id; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX index_ext_analytics_events_on_proximity_id ON ext_analytics_events USING btree (proximity_id);


--
-- Name: index_ext_analytics_events_on_timestamp; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX index_ext_analytics_events_on_timestamp ON ext_analytics_events USING btree ("timestamp");


--
-- Name: index_ext_analytics_events_on_user_id; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX index_ext_analytics_events_on_user_id ON ext_analytics_events USING btree (user_id);


--
-- Name: index_ext_kontakt_io_beacon_assignments_on_beacon_id; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX index_ext_kontakt_io_beacon_assignments_on_beacon_id ON ext_kontakt_io_beacon_assignments USING btree (beacon_id);


--
-- Name: index_mobile_devices_on_active; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX index_mobile_devices_on_active ON mobile_devices USING btree (active);


--
-- Name: index_mobile_devices_on_user_id; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX index_mobile_devices_on_user_id ON mobile_devices USING btree (user_id);


--
-- Name: index_oauth_access_grants_on_token; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE UNIQUE INDEX index_oauth_access_grants_on_token ON oauth_access_grants USING btree (token);


--
-- Name: index_oauth_access_tokens_on_refresh_token; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE UNIQUE INDEX index_oauth_access_tokens_on_refresh_token ON oauth_access_tokens USING btree (refresh_token);


--
-- Name: index_oauth_access_tokens_on_resource_owner_id; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX index_oauth_access_tokens_on_resource_owner_id ON oauth_access_tokens USING btree (resource_owner_id);


--
-- Name: index_oauth_access_tokens_on_token; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE UNIQUE INDEX index_oauth_access_tokens_on_token ON oauth_access_tokens USING btree (token);


--
-- Name: index_oauth_applications_on_uid; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE UNIQUE INDEX index_oauth_applications_on_uid ON oauth_applications USING btree (uid);


--
-- Name: index_password_archivable; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX index_password_archivable ON old_passwords USING btree (password_archivable_type, password_archivable_id);


--
-- Name: index_rpush_apps_on_application_id; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX index_rpush_apps_on_application_id ON rpush_apps USING btree (application_id);


--
-- Name: index_rpush_feedback_on_device_token; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX index_rpush_feedback_on_device_token ON rpush_feedback USING btree (device_token);


--
-- Name: index_rpush_notifications_multi; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX index_rpush_notifications_multi ON rpush_notifications USING btree (delivered, failed) WHERE ((NOT delivered) AND (NOT failed));


--
-- Name: index_triggers_on_activity_id; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX index_triggers_on_activity_id ON triggers USING btree (activity_id);


--
-- Name: index_triggers_on_application_id; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX index_triggers_on_application_id ON triggers USING btree (application_id);


--
-- Name: index_users_on_application_id; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX index_users_on_application_id ON users USING btree (application_id);


--
-- Name: index_users_on_application_id_and_client_id; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE INDEX index_users_on_application_id_and_client_id ON users USING btree (application_id, client_id);


--
-- Name: unique_schema_migrations; Type: INDEX; Schema: public; Owner: Wilson
--

CREATE UNIQUE INDEX unique_schema_migrations ON schema_migrations USING btree (version);


--
-- Name: applications_beacons applications_beacons_applications_fk; Type: FK CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY applications_beacons
    ADD CONSTRAINT applications_beacons_applications_fk FOREIGN KEY (application_id) REFERENCES applications(id);


--
-- Name: applications_beacons applications_beacons_beacons_fk; Type: FK CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY applications_beacons
    ADD CONSTRAINT applications_beacons_beacons_fk FOREIGN KEY (beacon_id) REFERENCES beacons(id);


--
-- Name: beacons beacons_admins_fk; Type: FK CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY beacons
    ADD CONSTRAINT beacons_admins_fk FOREIGN KEY (manager_id) REFERENCES admins(id);


--
-- Name: coupon_images coupon_images_coupons_fk; Type: FK CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY coupon_images
    ADD CONSTRAINT coupon_images_coupons_fk FOREIGN KEY (coupon_id) REFERENCES coupons(id);


--
-- Name: coupons coupons_activities_fk; Type: FK CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY coupons
    ADD CONSTRAINT coupons_activities_fk FOREIGN KEY (activity_id) REFERENCES activities(id);


--
-- Name: custom_attributes custom_attributes_activities_fk; Type: FK CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY custom_attributes
    ADD CONSTRAINT custom_attributes_activities_fk FOREIGN KEY (activity_id) REFERENCES activities(id);


--
-- Name: applications fk_rails_6661d1683d; Type: FK CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY applications
    ADD CONSTRAINT fk_rails_6661d1683d FOREIGN KEY (account_id) REFERENCES accounts(id);


--
-- Name: beacon_proximity_fields fk_rails_6c5799b47f; Type: FK CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY beacon_proximity_fields
    ADD CONSTRAINT fk_rails_6c5799b47f FOREIGN KEY (beacon_id) REFERENCES beacons(id);


--
-- Name: admins fk_rails_830a81c96a; Type: FK CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY admins
    ADD CONSTRAINT fk_rails_830a81c96a FOREIGN KEY (account_id) REFERENCES accounts(id);


--
-- Name: mobile_devices fk_rails_f73e485671; Type: FK CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY mobile_devices
    ADD CONSTRAINT fk_rails_f73e485671 FOREIGN KEY (user_id) REFERENCES users(id);


--
-- Name: beacon_configs fk_rails_f748eb8b0b; Type: FK CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY beacon_configs
    ADD CONSTRAINT fk_rails_f748eb8b0b FOREIGN KEY (beacon_id) REFERENCES beacons(id);


--
-- Name: users fk_rails_fbbd73badd; Type: FK CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk_rails_fbbd73badd FOREIGN KEY (application_id) REFERENCES applications(id);


--
-- Name: rpush_apps fk_rails_ffee22ef4e; Type: FK CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY rpush_apps
    ADD CONSTRAINT fk_rails_ffee22ef4e FOREIGN KEY (application_id) REFERENCES applications(id);


--
-- Name: beacons index_beacons_on_account_id; Type: FK CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY beacons
    ADD CONSTRAINT index_beacons_on_account_id FOREIGN KEY (account_id) REFERENCES accounts(id);


--
-- Name: oauth_applications oauth_applications_applications_fk; Type: FK CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY oauth_applications
    ADD CONSTRAINT oauth_applications_applications_fk FOREIGN KEY (application_id) REFERENCES applications(id);


--
-- Name: triggers triggers_activities_fk; Type: FK CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY triggers
    ADD CONSTRAINT triggers_activities_fk FOREIGN KEY (activity_id) REFERENCES activities(id);


--
-- Name: triggers triggers_applications_fk; Type: FK CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY triggers
    ADD CONSTRAINT triggers_applications_fk FOREIGN KEY (application_id) REFERENCES applications(id);


--
-- Name: triggers_sources triggers_sources_triggers_fk; Type: FK CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY triggers_sources
    ADD CONSTRAINT triggers_sources_triggers_fk FOREIGN KEY (trigger_id) REFERENCES triggers(id);


--
-- Name: zones zones_admins_fk; Type: FK CONSTRAINT; Schema: public; Owner: Wilson
--

ALTER TABLE ONLY zones
    ADD CONSTRAINT zones_admins_fk FOREIGN KEY (manager_id) REFERENCES admins(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--