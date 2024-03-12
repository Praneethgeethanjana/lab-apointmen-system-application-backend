package com.praneeth.lab.constants;

import com.praneeth.lab.entity.Appointment;
import com.praneeth.lab.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.praneeth.lab.constants.AppConstants.EmailBody.*;

@Component
public class EmailHtmlConstant {

    @Value("${base.url}")
    private static String baseUrl;





    public static String sendUserToVerificationLink(String url, User user) {
        return "<!DOCTYPE html>\n" +
                "\n" +
                "<html lang=\"en\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:v=\"urn:schemas-microsoft-com:vml\">\n" +
                "<head>\n" +
                "  <title></title>\n" +
                "  <meta charset=\"utf-8\"/>\n" +
                "  <meta content=\"width=device-width, initial-scale=1.0\" name=\"viewport\"/>\n" +
                "  <link href=\"https://fonts.googleapis.com/css?family=Montserrat\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "  <link href=\"https://fonts.googleapis.com/css?family=Playfair+Display\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "  <link href=\"https://fonts.googleapis.com/css?family=Bitter\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "  <link href=\"https://fonts.googleapis.com/css?family=Lato\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "  <link href=\"https://fonts.googleapis.com/css?family=Oswald\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "  <link href=\"https://fonts.googleapis.com/css?family=Droid+Serif\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "  <link href=\"https://fonts.googleapis.com/css?family=Oxygen\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "  <link href=\"https://fonts.googleapis.com/css?family=Abril+Fatface\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "  <link href=\"https://fonts.googleapis.com/css?family=Nunito\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "  <!--<![endif]-->\n" +
                "  <style>\n" +
                "    * {\n" +
                "      box-sizing: border-box;\n" +
                "    }\n" +
                "\n" +
                "    th.column {\n" +
                "      padding: 0\n" +
                "    }\n" +
                "\n" +
                "    a[x-apple-data-detectors] {\n" +
                "      color: inherit !important;\n" +
                "      text-decoration: inherit !important;\n" +
                "    }\n" +
                "\n" +
                "    #MessageViewBody a {\n" +
                "      color: inherit;\n" +
                "      text-decoration: none;\n" +
                "    }\n" +
                "\n" +
                "    p {\n" +
                "      line-height: inherit\n" +
                "    }\n" +
                "\n" +
                "    @media (max-width: 660px) {\n" +
                "      .icons-inner {\n" +
                "        text-align: center;\n" +
                "      }\n" +
                "\n" +
                "      .icons-inner td {\n" +
                "        margin: 0 auto;\n" +
                "      }\n" +
                "\n" +
                "      .fullMobileWidth,\n" +
                "      .row-content {\n" +
                "        width: 100% !important;\n" +
                "      }\n" +
                "\n" +
                "      .image_block img.big {\n" +
                "        width: auto !important;\n" +
                "      }\n" +
                "\n" +
                "      .stack .column {\n" +
                "        width: 100%;\n" +
                "        display: block;\n" +
                "      }\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body style=\"background-color: #f7f6f5; margin: 0; padding: 0; -webkit-text-size-adjust: none; text-size-adjust: none;\">\n" +
                "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"nl-container\" role=\"presentation\"\n" +
                "       style=\"mso-table-lspace: 0; mso-table-rspace: 0; background-color: #f7f6f5;\" width=\"100%\">\n" +
                "  <tbody>\n" +
                "  <tr>\n" +
                "    <td>\n" +
                "      <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-1\" role=\"presentation\"\n" +
                "             style=\"mso-table-lspace: 0; mso-table-rspace: 0;\" width=\"100%\">\n" +
                "        <tbody>\n" +
                "        <tr>\n" +
                "          <td>\n" +
                "            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\"\n" +
                "                   role=\"presentation\" style=\"mso-table-lspace: 0; mso-table-rspace: 0; background-color: #fff;\"\n" +
                "                   width=\"640\">\n" +
                "              <tbody>\n" +
                "              <tr>\n" +
                "                <th class=\"column\"\n" +
                "                    style=\"mso-table-lspace: 0; mso-table-rspace: 0; font-weight: 400; text-align: left; vertical-align: top;\"\n" +
                "                    width=\"100%\">\n" +
                "                  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"image_block\" role=\"presentation\"\n" +
                "                         style=\"mso-table-lspace: 0; mso-table-rspace: 0;\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td style=\"width:100%;padding-right:0px;padding-left:0px;padding-top:30px;padding-bottom:30px;\">\n" +
                "                        <div align=\"center\" style=\"line-height:10px\"><img class=\"fullMobileWidth big\"\n" +
                "                                                                          src=\"https://dev-recourse.jevigsoft.com/ancybtrading/email/logo.jpg\"\n" +
                "                                                                          style=\"display: block; height: auto; border: 0; width: 150px; max-width: 100%;\"\n" +
                "                                                                          width=\"128\"/></div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </th>\n" +
                "              </tr>\n" +
                "              </tbody>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "        </tbody>\n" +
                "      </table>\n" +
                "      <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-2\" role=\"presentation\"\n" +
                "             style=\"mso-table-lspace: 0; mso-table-rspace: 0;\" width=\"100%\">\n" +
                "        <tbody>\n" +
                "        <tr>\n" +
                "          <td>\n" +
                "            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\"\n" +
                "                   role=\"presentation\" style=\"mso-table-lspace: 0; mso-table-rspace: 0; background-color: #fff;\"\n" +
                "                   width=\"640\">\n" +
                "              <tbody>\n" +
                "              <tr>\n" +
                "                <th class=\"column\"\n" +
                "                    style=\"mso-table-lspace: 0; mso-table-rspace: 0; font-weight: 400; text-align: left; vertical-align: top;\"\n" +
                "                    width=\"100%\">\n" +
                "                  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"image_block\" role=\"presentation\"\n" +
                "                         style=\"mso-table-lspace: 0; mso-table-rspace: 0;\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td style=\"padding-bottom:20px;padding-left:15px; font-family: Oxygen, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;\">\n" +
                "                        <p style=\"margin-top:0px; margin-bottom: 7px; font-size: 15px; text-align: left; line-height: 1.2; \">Hey <b>" + user.getUserName() + ",</b></p>\n" +
                "                        <p style=\"margin-top: 15px;margin-bottom: 5px; font-size: 15px; text-align: left; line-height: 1.2;\">" + VERIFY_EMAIL_STARTING_LINE + "</p>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                      <td style=\"padding-bottom:20px;padding-left:15px;padding-right:15px;padding-top:0px;width:100%;\">\n" +
                "                        <div align=\"center\" style=\"line-height:10px\"><img class=\"fullMobileWidth big\"\n" +
                "                                                                          src=\"https://dev-recourse.jevigsoft.com/ancybtrading/email/hello.png\"\n" +
                "                                                                          style=\"display: block; height: auto; border: 0; width: 100%; max-width: 100%;\"\n" +
                "                                                                          width=\"480\"/></div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </th>\n" +
                "              </tr>\n" +
                "              </tbody>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "        </tbody>\n" +
                "      </table>\n" +
                "      <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-3\" role=\"presentation\"\n" +
                "             style=\"mso-table-lspace: 0; mso-table-rspace: 0;\" width=\"100%\">\n" +
                "        <tbody>\n" +
                "        <tr>\n" +
                "          <td>\n" +
                "            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\"\n" +
                "                   role=\"presentation\" style=\"mso-table-lspace: 0; mso-table-rspace: 0; background-color: #fff;\"\n" +
                "                   width=\"640\">\n" +
                "              <tbody>\n" +
                "              <tr>\n" +
                "                <th class=\"column\"\n" +
                "                    style=\"mso-table-lspace: 0; mso-table-rspace: 0; font-weight: 400; text-align: left; vertical-align: top;\"\n" +
                "                    width=\"100%\">\n" +
                "                  <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"text_block\" role=\"presentation\"\n" +
                "                         style=\"mso-table-lspace: 0; mso-table-rspace: 0; word-break: break-word;\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td>\n" +
                "                        <div style=\"font-family: Oxygen, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;\">\n" +
                "                          <div\n" +
                "                            style=\"font-size: 14px; color: #555555; line-height: 1.7;font-family: Oxygen, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;\">\n" +
                "                            <p style=\"margin: 0; margin-left: 5px; font-size: 16px; text-align: left;\">" + VERIFY_EMAIL_BODY + "</p>\n" +
                "                          </div>\n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </th>\n" +
                "              </tr>\n" +
                "              </tbody>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "        </tbody>\n" +
                "      </table>\n" +
                "      <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-3\" role=\"presentation\"\n" +
                "             style=\"mso-table-lspace: 0; mso-table-rspace: 0;\" width=\"100%\">\n" +
                "        <tbody>\n" +
                "        <tr>\n" +
                "          <td>\n" +
                "            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\"\n" +
                "                   role=\"presentation\" style=\"mso-table-lspace: 0; mso-table-rspace: 0; background-color: #fff;\"\n" +
                "                   width=\"640\">\n" +
                "              <tbody>\n" +
                "              <tr>\n" +
                "                <th class=\"column\"\n" +
                "                    style=\"mso-table-lspace: 0; mso-table-rspace: 0; font-weight: 400; text-align: left; vertical-align: top;\"\n" +
                "                    width=\"100%\">\n" +
                "                  <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"text_block\" role=\"presentation\"\n" +
                "                         style=\"margin-left: 5px;margin-right: 5px;mso-table-lspace: 0; mso-table-rspace: 0; word-break: break-word;\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td colspan=\"2\">\n" +
                "                        <div style=\"font-family: Oxygen, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;\">\n" +
                "                          <div style=\"font-size: 14px; color: #555555; line-height: 1.7;\">\n" +
                "\n" +
                "                            <p style=\"margin-top: 25px\">Verification Link: <a style=\"color: #AC3E7F; text-decoration: none\" href=\"" + url + "\">" + url + "</a></p>\n" +
                "                            <a href=\"" + url + "\" style=\"margin-top:15px; border-radius:5px; display:inline-block;background:#7367F0;color:#ffffff;font-size:14px;font-weight:900;line-height:15px;text-decoration:none;text-transform:none;padding:12px 25px;\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?q=https://app.binance.com/en/my/dashboard?utm_source%3Dcrm%26utm_medium%3Demail%26utm_campaign%3Dtransactional%26utm_content%3Demail_new_device_authorize2%26_dp%3DL3dlYnZpZXcvd2Vidmlldz90eXBlPWRlZmF1bHQmbmVlZExvZ2luPWZhbHNlJnVybD1hSFIwY0hNNkx5OTNkM2N1WW1sdVlXNWpaUzVqYjIwdlpXNHZiWGt2WkdGemFHSnZZWEprUDNWMGJWOXpiM1Z5WTJVOVkzSnRKblYwYlY5dFpXUnBkVzA5WlcxaGFXd21kWFJ0WDJOaGJYQmhhV2R1UFhSeVlXNXpZV04wYVc5dVlXd21kWFJ0WDJOdmJuUmxiblE5WlcxaGFXeGZibVYzWDJSbGRtbGpaVjloZFhSb2IzSnBlbVV5WDBOVVFURQ%3D%3D&amp;source=gmail&amp;ust=1661511340074000&amp;usg=AOvVaw3VIupT177-zvNNQHJ8xBB9\">\n" +
                "                              Verify Email\n" +
                "                            </a>\n" +
                "                          </div>\n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                  <table style=\"margin-top: 30px;width: 100%\">\n" +
                "                    <tbody><tr>\n" +
                "                      <td style=\"width: 100%\">\n" +
                "                        <div align=\"center\">\n" +
                "                          <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0; mso-table-rspace: 0;\" width=\"100%\">\n" +
                "                            <tbody><tr>\n" +
                "                              <td class=\"divider_inner\" style=\"font-size: 1px; line-height: 1px; border-top: 1px solid #d5d5d5;\"><span></span></td>\n" +
                "                            </tr>\n" +
                "                            </tbody></table>\n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                    </tbody>\n" +
                "                  </table>\n" +
                "                </th>\n" +
                "              </tr>\n" +
                "              </tbody>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "        </tbody>\n" +
                "      </table>\n" +
                "      <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-12\" role=\"presentation\"\n" +
                "             style=\"mso-table-lspace: 0; mso-table-rspace: 0; background-color: #f7f6f5;\" width=\"100%\">\n" +
                "        <tbody>\n" +
                "        <tr>\n" +
                "          <td>\n" +
                "            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\"\n" +
                "                   role=\"presentation\"\n" +
                "                   style=\"mso-table-lspace: 0; mso-table-rspace: 0; background-color: #ffffff; background-repeat: no-repeat;\"\n" +
                "                   width=\"640\">\n" +
                "              <tbody>\n" +
                "              <tr>\n" +
                "                <th class=\"column\"\n" +
                "                    style=\"mso-table-lspace: 0; mso-table-rspace: 0; font-weight: 400; text-align: left; vertical-align: top; padding-left: 5px; padding-right: 5px;\"\n" +
                "                    width=\"100%\">\n" +
                "                  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"social_block\" role=\"presentation\"\n" +
                "                         style=\"mso-table-lspace: 0; mso-table-rspace: 0;\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td\n" +
                "                        style=\"padding-bottom:35px;padding-left:10px;padding-right:10px;padding-top:5px;text-align:left; color: #5E5873\">\n" +
                "                        <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"social-table\"\n" +
                "                               role=\"presentation\" style=\"mso-table-lspace: 0; mso-table-rspace: 0;\" width=\"144px\">\n" +
                "                          <tr>\n" +
                "                            <p style=\"margin-bottom: 7px; font-size: 14px; text-align: left; line-height: 1.2; font-family: Oxygen, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;\">Thanks,</p>\n" +
                "                            <p style=\"margin-top: 0px;margin-bottom: 25px; font-size: 14px; text-align: left; line-height: 1.2; font-family: Oxygen, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;\">Team ABC Lab</p>\n" +
                "                          </tr>\n" +
                "                          <tr>\n" +
                "                            <td style=\"display: inline-flex;\">\n" +
                "                                <a href=\"https://www.facebook.com//\" target=\"_blank\" ><img alt=\"Facebook\" height=\"18\" src=\"https://mydonate-resources.ceyentra.lk/email-template/fb.png\" style=\"display:block;height:auto;border:0;width:22px\"  width=\"32\" class=\"CToWUd\" data-bit=\"iit\"></a>\n" +
                "                                <a href=\"https://twitter.com/\" target=\"_blank\"  style=\"margin: 0px 15px;\"><img alt=\"Instagram\" height=\"20\" src=\"https://mydonate-resources.ceyentra.lk/email-template/twiiter.png\" style=\"display:block;height:auto;border:0;width:22px\"  width=\"32\" class=\"CToWUd\" data-bit=\"iit\"></a>\n" +
                "                                <a href=\"https://www.instagram.com/\" target=\"_blank\"><img alt=\"LinkedIn\" height=\"20\" src=\"https://mydonate-resources.ceyentra.lk/email-template/instagram.png\" style=\"display:block;height:auto;border:0;width:22px\"  width=\"32\" class=\"CToWUd\" data-bit=\"iit\"></a>\n" +
                "                              </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </th>\n" +
                "              </tr>\n" +
                "              </tbody>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "        </tbody>\n" +
                "      </table>\n" +
                "    </td>\n" +
                "  </tr>\n" +
                "  </tbody>\n" +
                "</table>\n" +
                "</body>\n" +
                "</html>";
    }


    public static String sendForgotPasswordEmailBody(String url, User user) {
        return "<!DOCTYPE html>\n" +
                "\n" +
                "<html lang=\"en\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:v=\"urn:schemas-microsoft-com:vml\">\n" +
                "<head>\n" +
                "  <title></title>\n" +
                "  <meta charset=\"utf-8\"/>\n" +
                "  <meta content=\"width=device-width, initial-scale=1.0\" name=\"viewport\"/>\n" +
                "  <link href=\"https://fonts.googleapis.com/css?family=Montserrat\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "  <link href=\"https://fonts.googleapis.com/css?family=Playfair+Display\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "  <link href=\"https://fonts.googleapis.com/css?family=Bitter\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "  <link href=\"https://fonts.googleapis.com/css?family=Lato\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "  <link href=\"https://fonts.googleapis.com/css?family=Oswald\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "  <link href=\"https://fonts.googleapis.com/css?family=Droid+Serif\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "  <link href=\"https://fonts.googleapis.com/css?family=Oxygen\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "  <link href=\"https://fonts.googleapis.com/css?family=Abril+Fatface\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "  <link href=\"https://fonts.googleapis.com/css?family=Nunito\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "  <!--<![endif]-->\n" +
                "  <style>\n" +
                "    * {\n" +
                "      box-sizing: border-box;\n" +
                "    }\n" +
                "\n" +
                "    th.column {\n" +
                "      padding: 0\n" +
                "    }\n" +
                "\n" +
                "    a[x-apple-data-detectors] {\n" +
                "      color: inherit !important;\n" +
                "      text-decoration: inherit !important;\n" +
                "    }\n" +
                "\n" +
                "    #MessageViewBody a {\n" +
                "      color: inherit;\n" +
                "      text-decoration: none;\n" +
                "    }\n" +
                "\n" +
                "    p {\n" +
                "      line-height: inherit\n" +
                "    }\n" +
                "\n" +
                "    @media (max-width: 660px) {\n" +
                "      .icons-inner {\n" +
                "        text-align: center;\n" +
                "      }\n" +
                "\n" +
                "      .icons-inner td {\n" +
                "        margin: 0 auto;\n" +
                "      }\n" +
                "\n" +
                "      .fullMobileWidth,\n" +
                "      .row-content {\n" +
                "        width: 100% !important;\n" +
                "      }\n" +
                "\n" +
                "      .image_block img.big {\n" +
                "        width: auto !important;\n" +
                "      }\n" +
                "\n" +
                "      .stack .column {\n" +
                "        width: 100%;\n" +
                "        display: block;\n" +
                "      }\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body style=\"background-color: #f7f6f5; margin: 0; padding: 0; -webkit-text-size-adjust: none; text-size-adjust: none;\">\n" +
                "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"nl-container\" role=\"presentation\"\n" +
                "       style=\"mso-table-lspace: 0; mso-table-rspace: 0; background-color: #f7f6f5;\" width=\"100%\">\n" +
                "  <tbody>\n" +
                "  <tr>\n" +
                "    <td>\n" +
                "      <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-1\" role=\"presentation\"\n" +
                "             style=\"mso-table-lspace: 0; mso-table-rspace: 0;\" width=\"100%\">\n" +
                "        <tbody>\n" +
                "        <tr>\n" +
                "          <td>\n" +
                "            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\"\n" +
                "                   role=\"presentation\" style=\"mso-table-lspace: 0; mso-table-rspace: 0; background-color: #fff;\"\n" +
                "                   width=\"640\">\n" +
                "              <tbody>\n" +
                "              <tr>\n" +
                "                <th class=\"column\"\n" +
                "                    style=\"mso-table-lspace: 0; mso-table-rspace: 0; font-weight: 400; text-align: left; vertical-align: top;\"\n" +
                "                    width=\"100%\">\n" +
                "                  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"image_block\" role=\"presentation\"\n" +
                "                         style=\"mso-table-lspace: 0; mso-table-rspace: 0;\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td style=\"width:100%;padding-right:0px;padding-left:0px;padding-top:30px;padding-bottom:30px;\">\n" +
                "                        <div align=\"center\" style=\"line-height:10px\"><img class=\"fullMobileWidth big\"\n" +
                "                                                                          src=\"https://dev-recourse.jevigsoft.com/ancybtrading/email/logo.jpg\"\n" +
                "                                                                          style=\"display: block; height: auto; border: 0; width: 150px; max-width: 100%;\"\n" +
                "                                                                          width=\"128\"/></div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </th>\n" +
                "              </tr>\n" +
                "              </tbody>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "        </tbody>\n" +
                "      </table>\n" +
                "      <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-2\" role=\"presentation\"\n" +
                "             style=\"mso-table-lspace: 0; mso-table-rspace: 0;\" width=\"100%\">\n" +
                "        <tbody>\n" +
                "        <tr>\n" +
                "          <td>\n" +
                "            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\"\n" +
                "                   role=\"presentation\" style=\"mso-table-lspace: 0; mso-table-rspace: 0; background-color: #fff;\"\n" +
                "                   width=\"640\">\n" +
                "              <tbody>\n" +
                "              <tr>\n" +
                "                <th class=\"column\"\n" +
                "                    style=\"mso-table-lspace: 0; mso-table-rspace: 0; font-weight: 400; text-align: left; vertical-align: top;\"\n" +
                "                    width=\"100%\">\n" +
                "                  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"image_block\" role=\"presentation\"\n" +
                "                         style=\"mso-table-lspace: 0; mso-table-rspace: 0;\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td style=\"padding-bottom:20px;padding-left:15px; font-family: Oxygen, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;\">\n" +
                "                        <p style=\"margin-top:0px; margin-bottom: 7px; font-size: 15px; text-align: left; line-height: 1.2; \">Hey <b>" + user.getUserName() + ",</b></p>\n" +
                "                        <p style=\"margin-top: 15px;margin-bottom: 5px; font-size: 15px; text-align: left; line-height: 1.2;\">" + RESET_USER_PASSWORD_STARTING_LINE + "</p>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                      <td style=\"padding-bottom:20px;padding-left:15px;padding-right:15px;padding-top:0px;width:100%;\">\n" +
                "                        <div align=\"center\" style=\"line-height:10px\"><img class=\"fullMobileWidth big\"\n" +
                "                                                                          src=\"https://dev-recourse.jevigsoft.com/ancybtrading/email/hello.png\"\n" +
                "                                                                          style=\"display: block; height: auto; border: 0; width: 100%; max-width: 100%;\"\n" +
                "                                                                          width=\"480\"/></div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </th>\n" +
                "              </tr>\n" +
                "              </tbody>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "        </tbody>\n" +
                "      </table>\n" +
                "      <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-3\" role=\"presentation\"\n" +
                "             style=\"mso-table-lspace: 0; mso-table-rspace: 0;\" width=\"100%\">\n" +
                "        <tbody>\n" +
                "        <tr>\n" +
                "          <td>\n" +
                "            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\"\n" +
                "                   role=\"presentation\" style=\"mso-table-lspace: 0; mso-table-rspace: 0; background-color: #fff;\"\n" +
                "                   width=\"640\">\n" +
                "              <tbody>\n" +
                "              <tr>\n" +
                "                <th class=\"column\"\n" +
                "                    style=\"mso-table-lspace: 0; mso-table-rspace: 0; font-weight: 400; text-align: left; vertical-align: top;\"\n" +
                "                    width=\"100%\">\n" +
                "                  <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"text_block\" role=\"presentation\"\n" +
                "                         style=\"mso-table-lspace: 0; mso-table-rspace: 0; word-break: break-word;\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td>\n" +
                "                        <div style=\"font-family: Oxygen, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;\">\n" +
                "                          <div\n" +
                "                            style=\"font-size: 14px; color: #555555; line-height: 1.7;font-family: Oxygen, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;\">\n" +
                "                            <p style=\"margin: 0; margin-left: 5px; font-size: 16px; text-align: left;\">" + RESET_PASSWORD_STARTING_BODY + "</p>\n" +
                "                          </div>\n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </th>\n" +
                "              </tr>\n" +
                "              </tbody>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "        </tbody>\n" +
                "      </table>\n" +
                "      <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-3\" role=\"presentation\"\n" +
                "             style=\"mso-table-lspace: 0; mso-table-rspace: 0;\" width=\"100%\">\n" +
                "        <tbody>\n" +
                "        <tr>\n" +
                "          <td>\n" +
                "            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\"\n" +
                "                   role=\"presentation\" style=\"mso-table-lspace: 0; mso-table-rspace: 0; background-color: #fff;\"\n" +
                "                   width=\"640\">\n" +
                "              <tbody>\n" +
                "              <tr>\n" +
                "                <th class=\"column\"\n" +
                "                    style=\"mso-table-lspace: 0; mso-table-rspace: 0; font-weight: 400; text-align: left; vertical-align: top;\"\n" +
                "                    width=\"100%\">\n" +
                "                  <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"text_block\" role=\"presentation\"\n" +
                "                         style=\"margin-left: 5px;margin-right: 5px;mso-table-lspace: 0; mso-table-rspace: 0; word-break: break-word;\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td colspan=\"2\">\n" +
                "                        <div style=\"font-family: Oxygen, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;\">\n" +
                "                          <div style=\"font-size: 14px; color: #555555; line-height: 1.7;\">\n" +
                "\n" +
                "                            <p style=\"margin-top: 25px\">Verification Link: <a style=\"color: #AC3E7F; text-decoration: none\" href=\"" + url + "\">" + url + "</a></p>\n" +
                "                            <a href=\"" + url + "\" style=\"margin-top:15px; border-radius:5px; display:inline-block;background:#7367F0;color:#ffffff;font-size:14px;font-weight:900;line-height:15px;text-decoration:none;text-transform:none;padding:12px 25px;\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?q=https://app.binance.com/en/my/dashboard?utm_source%3Dcrm%26utm_medium%3Demail%26utm_campaign%3Dtransactional%26utm_content%3Demail_new_device_authorize2%26_dp%3DL3dlYnZpZXcvd2Vidmlldz90eXBlPWRlZmF1bHQmbmVlZExvZ2luPWZhbHNlJnVybD1hSFIwY0hNNkx5OTNkM2N1WW1sdVlXNWpaUzVqYjIwdlpXNHZiWGt2WkdGemFHSnZZWEprUDNWMGJWOXpiM1Z5WTJVOVkzSnRKblYwYlY5dFpXUnBkVzA5WlcxaGFXd21kWFJ0WDJOaGJYQmhhV2R1UFhSeVlXNXpZV04wYVc5dVlXd21kWFJ0WDJOdmJuUmxiblE5WlcxaGFXeGZibVYzWDJSbGRtbGpaVjloZFhSb2IzSnBlbVV5WDBOVVFURQ%3D%3D&amp;source=gmail&amp;ust=1661511340074000&amp;usg=AOvVaw3VIupT177-zvNNQHJ8xBB9\">\n" +
                "                              Verify Email\n" +
                "                            </a>\n" +
                "                          </div>\n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                  <table style=\"margin-top: 30px;width: 100%\">\n" +
                "                    <tbody><tr>\n" +
                "                      <td style=\"width: 100%\">\n" +
                "                        <div align=\"center\">\n" +
                "                          <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0; mso-table-rspace: 0;\" width=\"100%\">\n" +
                "                            <tbody><tr>\n" +
                "                              <td class=\"divider_inner\" style=\"font-size: 1px; line-height: 1px; border-top: 1px solid #d5d5d5;\"><span></span></td>\n" +
                "                            </tr>\n" +
                "                            </tbody></table>\n" +
                "                        </div>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                    </tbody>\n" +
                "                  </table>\n" +
                "                </th>\n" +
                "              </tr>\n" +
                "              </tbody>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "        </tbody>\n" +
                "      </table>\n" +
                "      <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-12\" role=\"presentation\"\n" +
                "             style=\"mso-table-lspace: 0; mso-table-rspace: 0; background-color: #f7f6f5;\" width=\"100%\">\n" +
                "        <tbody>\n" +
                "        <tr>\n" +
                "          <td>\n" +
                "            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\"\n" +
                "                   role=\"presentation\"\n" +
                "                   style=\"mso-table-lspace: 0; mso-table-rspace: 0; background-color: #ffffff; background-repeat: no-repeat;\"\n" +
                "                   width=\"640\">\n" +
                "              <tbody>\n" +
                "              <tr>\n" +
                "                <th class=\"column\"\n" +
                "                    style=\"mso-table-lspace: 0; mso-table-rspace: 0; font-weight: 400; text-align: left; vertical-align: top; padding-left: 5px; padding-right: 5px;\"\n" +
                "                    width=\"100%\">\n" +
                "                  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"social_block\" role=\"presentation\"\n" +
                "                         style=\"mso-table-lspace: 0; mso-table-rspace: 0;\" width=\"100%\">\n" +
                "                    <tr>\n" +
                "                      <td\n" +
                "                        style=\"padding-bottom:35px;padding-left:10px;padding-right:10px;padding-top:5px;text-align:left; color: #5E5873\">\n" +
                "                        <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"social-table\"\n" +
                "                               role=\"presentation\" style=\"mso-table-lspace: 0; mso-table-rspace: 0;\" width=\"144px\">\n" +
                "                          <tr>\n" +
                "                            <p style=\"margin-bottom: 7px; font-size: 14px; text-align: left; line-height: 1.2; font-family: Oxygen, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;\">Thanks,</p>\n" +
                "                            <p style=\"margin-top: 0px;margin-bottom: 25px; font-size: 14px; text-align: left; line-height: 1.2; font-family: Oxygen, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;\">Team Ancybrading.com</p>\n" +
                "                          </tr>\n" +
                "                          <tr>\n" +
                "                            <td style=\"display: inline-flex;\">\n" +
                "                                <a href=\"https://www.facebook.com//\" target=\"_blank\" ><img alt=\"Facebook\" height=\"18\" src=\"https://mydonate-resources.ceyentra.lk/email-template/fb.png\" style=\"display:block;height:auto;border:0;width:22px\"  width=\"32\" class=\"CToWUd\" data-bit=\"iit\"></a>\n" +
                "                                <a href=\"https://twitter.com/\" target=\"_blank\"  style=\"margin: 0px 15px;\"><img alt=\"Instagram\" height=\"20\" src=\"https://mydonate-resources.ceyentra.lk/email-template/twiiter.png\" style=\"display:block;height:auto;border:0;width:22px\"  width=\"32\" class=\"CToWUd\" data-bit=\"iit\"></a>\n" +
                "                                <a href=\"https://www.instagram.com/\" target=\"_blank\"><img alt=\"LinkedIn\" height=\"20\" src=\"https://mydonate-resources.ceyentra.lk/email-template/instagram.png\" style=\"display:block;height:auto;border:0;width:22px\"  width=\"32\" class=\"CToWUd\" data-bit=\"iit\"></a>\n" +
                "                              </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </th>\n" +
                "              </tr>\n" +
                "              </tbody>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "        </tbody>\n" +
                "      </table>\n" +
                "    </td>\n" +
                "  </tr>\n" +
                "  </tbody>\n" +
                "</table>\n" +
                "</body>\n" +
                "</html>";
    }


    public static String sendAppointmentApprovedEmail(Appointment appointment) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Appointment Approval Confirmation</title>\n" +
                "</head>\n" +
                "<body style=\"font-family: 'Arial', sans-serif;\">\n" +
                "\n" +
                "    <div style=\"max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ccc;\">\n" +
                "\n" +
                "        <h2 style=\"color: #333;\">Appointment Approved</h2>\n" +
                "\n" +
                "        <p>Dear "+appointment.getUser().getFirstName()+" "+appointment.getUser().getLastName()+",</p>\n" +
                "\n" +
                "        <p>Your appointment has been approved. Below are the details:</p>\n" +
                "\n" +
                "        <table style=\"width: 100%; margin-top: 20px; border-collapse: collapse;\">\n" +
                "            <tr>\n" +
                "                <td style=\"padding: 8px; border: 1px solid #ccc; background-color: #f2f2f2;\">Appointment Date & Time:</td>\n" +
                "                <td style=\"padding: 8px; border: 1px solid #ccc;\">"+appointment.getAppointmentDate().toString()+"</td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td style=\"padding: 8px; border: 1px solid #ccc; background-color: #f2f2f2;\">Lab Location:</td>\n" +
                "                <td style=\"padding: 8px; border: 1px solid #ccc;\">Colombo</td>\n" +
                "            </tr>\n" +
                "            <!-- Add more appointment details as needed -->\n" +
                "        </table>\n" +
                "\n" +
                "        <p>We look forward to seeing you at the scheduled appointment. If you have any questions or need further assistance, feel free to contact us.</p>\n" +
                "\n" +
                "        <p>Best regards,<br> ABC laboratory</p>\n" +
                "\n" +
                "    </div>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
    }


}
