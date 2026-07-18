/**
 * 车辆品牌 Logo 映射（对应 /img/brands/{slug}.svg）
 */
const VehicleBrandLogo = (function () {
    const BRAND_SLUG = {
        '特斯拉': 'tesla',
        '比亚迪': 'byd',
        '理想': 'lixiang',
        '大众': 'volkswagen',
        '宝马': 'bmw',
        '奔驰': 'mercedes',
        '奥迪': 'audi',
        '丰田': 'toyota',
        '本田': 'honda',
        '日产': 'nissan',
        '福特': 'ford',
        '雪佛兰': 'chevrolet',
        '现代': 'hyundai',
        '起亚': 'kia',
        '沃尔沃': 'volvo',
        '保时捷': 'porsche',
        '雷克萨斯': 'lexus',
        '凯迪拉克': 'cadillac',
        '别克': 'buick',
        '吉利': 'geely',
        '长安': 'changan',
        '蔚来': 'nio',
        '小鹏': 'xpeng',
        '问界': 'aito',
        '小米': 'xiaomi'
    };

    function escapeAttr(s) {
        return String(s)
            .replace(/&/g, '&amp;')
            .replace(/"/g, '&quot;')
            .replace(/</g, '&lt;');
    }

    function slugOf(brand) {
        if (!brand) return null;
        return BRAND_SLUG[brand.trim()] || null;
    }

    /**
     * @param {string} brand 品牌中文名
     * @param {string} extraClass 附加 class
     * @returns {string} HTML
     */
    function render(brand, extraClass) {
        const slug = slugOf(brand);
        const cls = 'vp-brand-logo' + (extraClass ? ' ' + extraClass : '');
        if (slug) {
            return '<img src="/img/brands/' + slug + '.svg" alt="' + escapeAttr(brand || '品牌') +
                '" class="' + cls + '" loading="lazy" decoding="async"/>';
        }
        const letter = (brand || '?').slice(0, 1);
        return '<span class="vp-brand-logo-fallback ' + (extraClass || '') + '">' + letter + '</span>';
    }

    return { render: render, slugOf: slugOf, BRAND_SLUG: BRAND_SLUG };
})();
