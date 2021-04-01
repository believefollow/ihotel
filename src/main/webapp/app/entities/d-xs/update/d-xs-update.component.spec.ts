jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DXsService } from '../service/d-xs.service';
import { IDXs, DXs } from '../d-xs.model';

import { DXsUpdateComponent } from './d-xs-update.component';

describe('Component Tests', () => {
  describe('DXs Management Update Component', () => {
    let comp: DXsUpdateComponent;
    let fixture: ComponentFixture<DXsUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let dXsService: DXsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DXsUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DXsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DXsUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      dXsService = TestBed.inject(DXsService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const dXs: IDXs = { id: 456 };

        activatedRoute.data = of({ dXs });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(dXs));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dXs = { id: 123 };
        spyOn(dXsService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dXs });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dXs }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(dXsService.update).toHaveBeenCalledWith(dXs);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dXs = new DXs();
        spyOn(dXsService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dXs });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dXs }));
        saveSubject.complete();

        // THEN
        expect(dXsService.create).toHaveBeenCalledWith(dXs);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dXs = { id: 123 };
        spyOn(dXsService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dXs });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(dXsService.update).toHaveBeenCalledWith(dXs);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
