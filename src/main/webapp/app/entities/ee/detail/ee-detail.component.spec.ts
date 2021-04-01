import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EeDetailComponent } from './ee-detail.component';

describe('Component Tests', () => {
  describe('Ee Management Detail Component', () => {
    let comp: EeDetailComponent;
    let fixture: ComponentFixture<EeDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [EeDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ ee: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(EeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load ee on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ee).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
